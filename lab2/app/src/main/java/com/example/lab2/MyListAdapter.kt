package com.example.lab2

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.net.HttpURLConnection
import java.net.URL

class MyListAdapter(context: Context, resource: Int, val objects: MutableList<Technology>) :
    ArrayAdapter<Technology>(context, resource, objects) {

    init {
        MyAsyncTask(objects).execute()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val tech = getItem(position)
        var view : View? = convertView

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_element, null)
        }

        (view?.findViewById(R.id.listText) as TextView).text = tech?.name

        val temp = tech?.bitmap
        (view.findViewById(R.id.listImage) as ImageView).setImageBitmap(Bitmap.createScaledBitmap(temp!!, 128, 128, true))

        return view
    }

    inner class MyAsyncTask(val objects: MutableList<Technology>) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void): Void? {

            for (tech in objects) {
                val conn = URL("https://raw.githubusercontent.com/wesleywerner/ancient-tech/" +
                        "02decf875616dd9692b31658d92e64a20d99f816/src/images/tech/" + tech.image).openConnection() as HttpURLConnection

                if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = conn.getInputStream()
                    tech.bitmap = BitmapFactory.decodeStream(inputStream)
                }
                publishProgress()
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Void?) {
            notifyDataSetChanged()
        }
    }

}