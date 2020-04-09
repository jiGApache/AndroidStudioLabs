package com.example.lab2

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment


class MyFragmentElement : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment, container, false)

        val arguments = arguments
        if (arguments != null) {
            val name = arguments.getString("name")
            val helpText = arguments.getString("helpText")
            val bitmap = arguments.getParcelable<Bitmap>("bitmap")

            val nameTextView : TextView = view.findViewById(R.id.technologyName)
            val helpTextView : TextView = view.findViewById(R.id.helpText)
            val imageView : ImageView = view.findViewById(R.id.image)

            nameTextView.text = name
            helpTextView.text = helpText
            imageView.setImageBitmap(bitmap)
        }
        return view
    }
}