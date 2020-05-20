package com.example.lab5

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley


class MainActivity : AppCompatActivity() {
    lateinit var URL: String
    lateinit var toolbar : Toolbar
    lateinit var catSpinner: Spinner
    lateinit var button: Button
    lateinit var mRecyclerView: RecyclerView
    lateinit var mCatAdapter: CatAdapter
    lateinit var mCatList: ArrayList<CatClass>
    lateinit var mRequestQueue: RequestQueue
    lateinit var sPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById<Toolbar>(R.id.catsToolBar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Cats <3"

        mRequestQueue = Volley.newRequestQueue(this)

        catSpinner = findViewById(R.id.catSpinner)

        //Здесь открывается последняя сохраненная форма
        sPref = this.getSharedPreferences("savedForm", Context.MODE_PRIVATE)
        editor = sPref.edit()
        if(sPref.contains("lastOpenedForm") && sPref.contains("lastOpenedURL")){
            catSpinner.setSelection(sPref.getInt("lastOpenedForm", 0))
            URL = sPref.getString("lastOpenedURL", "")!!
            parseJSON()
        }
        ////////////////////////////////////////////////////////////////////////////

        button = findViewById(R.id.find_cats_button)

        mCatList = ArrayList()
        mCatAdapter = CatAdapter(this, mCatList, mRequestQueue)

        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.adapter = mCatAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        button.setOnClickListener{
            mCatList.clear()
            val builder = Uri.Builder()
            builder.scheme("https://api.thecatapi.com")
                .appendPath("v1")
                .appendPath("images")
                .appendPath("search")
                .appendQueryParameter("api_key", "1c676b83-999f-4f44-bd6b-1b2c972aa1e3")
                .appendQueryParameter("limit", "100")
                .appendQueryParameter("breed_ids", resources.getStringArray(R.array.cat_ids).elementAt(catSpinner.selectedItemPosition)
                )
            URL = builder.build().toString()

            editor.putInt("lastOpenedForm", catSpinner.selectedItemPosition)
            editor.putString("lastOpenedURL", URL)
            editor.commit()
            parseJSON()
        }
    }

    private fun parseJSON() {
        val request = JsonArrayRequest(Request.Method.GET, URL, null,
            Response.Listener{
                response ->
                    var count = 0
                    while(count < response.length()){
                        val jsonObject = response.getJSONObject(count)
                        val imageUrl = jsonObject.getString("url")
                        val imageId = jsonObject.getString("id")
                        mCatList.add(CatClass(imageUrl, imageId))
                        count++
                    }
                    mCatAdapter = CatAdapter(this, mCatList, mRequestQueue)
                    mRecyclerView.adapter = mCatAdapter
            }, Response.ErrorListener() {
                    error->
                        error.printStackTrace()
            })
        mRequestQueue.add(request)
    }

    fun onLikedButtonCLicked(view: View){
        val intentToLikedCats = Intent(this, LikedCatsActivity::class.java)
        startActivity(intentToLikedCats)
    }
}
