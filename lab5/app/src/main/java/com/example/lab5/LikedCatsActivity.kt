package com.example.lab5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LikedCatsActivity : AppCompatActivity() {
    lateinit var toolbar : Toolbar
    lateinit var mRecyclerView: RecyclerView
    lateinit var mCatAdapter: LikedCatsAdapter
    lateinit var mCatList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liked_cats)

        toolbar = findViewById<Toolbar>(R.id.likedCatsToolBar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Last 10 liked cats"

        mCatList = DBHelper(this).getImageUrls()
        mCatAdapter = LikedCatsAdapter(this, mCatList)

        mRecyclerView = findViewById(R.id.liked_recycler_view)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.adapter = mCatAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}
