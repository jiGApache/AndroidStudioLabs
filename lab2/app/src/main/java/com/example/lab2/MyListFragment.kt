package com.example.lab2

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.ListFragment
import androidx.viewpager.widget.ViewPager
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.atomic.AtomicReferenceArray

class MyListFragment : ListFragment() {

    companion object {
        val technolist : ArrayList<Technology> = ArrayList<Technology>()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val context = activity as Context

        val list : ArrayList<String>? = arguments?.getStringArrayList("json")

        if (technolist.isEmpty())
            toTechnology(list!!, technolist)

        val adapter : ListAdapter = MyListAdapter(context, R.layout.list_element, technolist)
        listAdapter = adapter
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)

        val frag = MyFragment(technolist, position)

        requireActivity().supportFragmentManager.beginTransaction().addToBackStack("abc").replace(R.id.linearLayout, frag).commit()

    }

    fun toTechnology(list : ArrayList<String>, technolist : ArrayList<Technology>) {
        var image : String = ""
        var name : String = ""
        var helpText : String = ""
        var bitmap : Bitmap? = null

        var i : Int = 0
        while (i < 718) {
            if (Regex("\\s{8}\"graphic\".+").matches(list[i].substring(0, list[i].lastIndex))) {
                image = list[i].substring(20, list[i].lastIndex - 2)
//                Log.d("abc", image)
            }
            else if (Regex("\\s{8}\"helptext\".+").matches(list[i].substring(0, list[i].lastIndex))) {
                helpText = list[i].substring(21, list[i].lastIndex - 2)
//                Log.d("abc", helpText)
            }
            else if (Regex("\\s{8}\"name\".+").matches(list[i].substring(0, list[i].lastIndex))) {
                name = list[i].substring(17, list[i].lastIndex - 2)
                if (name == "?tech:Railroad")
                    name = "Railroad"
//                Log.d("abc", name)
            }

            if ((image != "") and (name != "")) {
                bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(view?.resources, R.drawable.broken), 128, 128, true)
                technolist.add(Technology(name, image, helpText, bitmap))
                image = ""
                name = ""
                helpText = ""
            }
            i++
        }
    }

}