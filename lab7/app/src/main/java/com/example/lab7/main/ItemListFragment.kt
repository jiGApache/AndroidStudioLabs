package com.example.lab7.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab7.R
import com.example.lab7.data.DataProcessor


class ItemListFragment<T:RecyclerView.ViewHolder> : Fragment() {

    private lateinit var adapter: StoreAdapter<T>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        root.findViewById<RecyclerView>(R.id.rv).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ItemListFragment.adapter.apply {
                DataProcessor.getInstance(context).startListening(this)
            }
        }
        return root
    }

    override fun onDestroyView() {
        DataProcessor.getInstance(context!!).stopListening(view?.findViewById<RecyclerView>(R.id.rv)?.adapter as StoreAdapter<T>)
        super.onDestroyView()
    }

    companion object {

        @JvmStatic
        fun <T: RecyclerView.ViewHolder> newInstance(adapter: StoreAdapter<T>): ItemListFragment<T> {
            return ItemListFragment<T>().apply {
                    this.adapter = adapter
            }
        }
    }
}