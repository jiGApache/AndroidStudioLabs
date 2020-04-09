package com.example.lab1_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    companion object{
        var threadStarted : Boolean = false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!threadStarted) {
            threadStarted = true
            Thread {
                Thread.sleep(2000)
                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
            }.start()
        }
    }

    override fun onRestart() {
        super.onRestart()
        finish()
    }
}
