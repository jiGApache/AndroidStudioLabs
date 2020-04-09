package com.example.lab2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import java.text.FieldPosition

class MyFragment(val technonList : ArrayList<Technology>, val position : Int) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.view_pager, container, false)
        val myAdapter = MyPageAdapter(requireActivity().supportFragmentManager, technonList)
        (view.findViewById(R.id.viewPager) as ViewPager).adapter = myAdapter
        (view.findViewById(R.id.viewPager) as ViewPager).setCurrentItem(position)
        return view
    }


}