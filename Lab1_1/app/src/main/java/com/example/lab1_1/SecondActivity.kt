package com.example.lab1_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_second.*
import java.lang.reflect.Array

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val users : ArrayList<String> = ArrayList()
        for(i in 1..100){
            users.add("String #$i")
        }

        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = UsersAdapter(users)
    }
}

