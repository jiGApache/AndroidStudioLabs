package com.example.lab2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager

class MainActivity : AppCompatActivity() {

    val listFrag = MyListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = intent.getStringArrayListExtra("json")

        val bundle = Bundle()
        bundle.putStringArrayList("json", list)
        listFrag.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.linearLayout, listFrag).commit()
    }
}
