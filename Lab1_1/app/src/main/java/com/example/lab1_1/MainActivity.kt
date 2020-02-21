package com.example.lab1_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread{
            Thread.sleep(2000)
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }.start()


    }
}
