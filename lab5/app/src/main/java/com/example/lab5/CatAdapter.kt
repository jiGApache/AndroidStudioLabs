package com.example.lab5

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.squareup.picasso.Picasso
import org.json.JSONObject


class CatAdapter(context: Context, catList: ArrayList<CatClass>, requestQueue: RequestQueue) :
    RecyclerView.Adapter<CatAdapter.CatViewHolder>() {
    private val mContext: Context
    private val mCatList: ArrayList<CatClass>
    private val sPref: SharedPreferences
    private val editor: SharedPreferences.Editor
    private val mRequestQueue: RequestQueue
    init {
        mContext = context
        mCatList = catList
        sPref = mContext.getSharedPreferences("pictureIDs", Context.MODE_PRIVATE)
        editor = sPref.edit()
        mRequestQueue = requestQueue
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val v: View = LayoutInflater.from(mContext).inflate(R.layout.cat_item, parent, false)
        return CatViewHolder(v)
    }

    fun sendVote(vote: Int, imageId: String){
        var URL: String
        val builder = Uri.Builder()
        builder.scheme("https://api.thecatapi.com")
            .appendPath("v1")
            .appendPath("votes")
            .appendQueryParameter("api_key", "1c676b83-999f-4f44-bd6b-1b2c972aa1e3")
        URL = builder.build().toString()

        Log.d("ttt", URL)

        val jsonObject = JSONObject()
        jsonObject.put("image_id", "$imageId")
        jsonObject.put("value", "$vote")

        val request = JsonObjectRequest(com.android.volley.Request.Method.POST, URL, jsonObject,
            Response.Listener {
                response ->
                    Toast.makeText(mContext, if(vote == 1) "Liked!" else "Disliked", Toast.LENGTH_SHORT).show()
            }, Response.ErrorListener {
                error->
                Toast.makeText(mContext, "${error}", Toast.LENGTH_SHORT).show()
                    error.printStackTrace()
            })
        mRequestQueue.add(request)
    }

    fun configureLastTen(vote: Int, imageUrl: String){
        if(vote == 1){
            if(DBHelper(mContext).getImageUrls().size >= 10){
                DBHelper(mContext).deleteFirstUrl()
                DBHelper(mContext).insertImageUrl(imageUrl)
            } else {
                DBHelper(mContext).insertImageUrl(imageUrl)
            }
        } else {
            DBHelper(mContext).deleteImageByUrl(imageUrl)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val currentItem: CatClass = mCatList[position]
        val imageUrl: String = currentItem.mCatImageURL
        //Ставим картинку
        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView)

        //Устанавливаем фон и доступность кнопок элементов RecyclerView
        if(sPref.contains(currentItem.pictureId)){
            if(sPref.getBoolean(currentItem.pictureId, false) == true){
                holder.buttonLike.backgroundTintList = mContext.resources.getColorStateList(R.drawable.like_background_color)
                holder.buttonDislike.backgroundTintList = mContext.resources.getColorStateList(R.drawable.nonchecked_background_color)
                holder.buttonLike.isClickable = false
                holder.buttonDislike.isClickable = true
            } else {
                holder.buttonLike.backgroundTintList = mContext.resources.getColorStateList(R.drawable.nonchecked_background_color)
                holder.buttonDislike.backgroundTintList = mContext.resources.getColorStateList(R.drawable.dislike_background_color)
                holder.buttonLike.isClickable = true
                holder.buttonDislike.isClickable = false
            }
        } else {
            holder.buttonLike.backgroundTintList = mContext.resources.getColorStateList(R.drawable.nonchecked_background_color)
            holder.buttonDislike.backgroundTintList = mContext.resources.getColorStateList(R.drawable.nonchecked_background_color)
            holder.buttonLike.isClickable = true
            holder.buttonDislike.isClickable = true
        }

        //Нажатие на кнопку LIKE
        holder.buttonLike.setOnClickListener {
            //отправляем запрос на оценивание картинки
            sendVote(1,  currentItem.pictureId)

            holder.buttonLike.backgroundTintList = mContext.resources.getColorStateList(R.drawable.like_background_color)
            holder.buttonDislike.backgroundTintList = mContext.resources.getColorStateList(R.drawable.nonchecked_background_color)
            holder.buttonLike.isClickable = false
            holder.buttonDislike.isClickable = true
            if(sPref.contains("${currentItem.pictureId}")) {
                if(sPref.getBoolean("${currentItem.pictureId}", false) == false){
                    editor.remove("${currentItem.pictureId}")
                    editor.commit()
                    editor.putBoolean("${currentItem.pictureId}", true)
                    editor.commit()
                }
            } else {
                editor.putBoolean(currentItem.pictureId, true)
                editor.commit()
            }

            //Делаем актуальными последние десять лайков
            configureLastTen(1, currentItem.mCatImageURL)
        }

        //Нажатие на кнопку DISLIKE
        holder.buttonDislike.setOnClickListener{
            //отправляем запрос на оценивание картинки
            sendVote(0, currentItem.pictureId)

            holder.buttonLike.backgroundTintList = mContext.resources.getColorStateList(R.drawable.nonchecked_background_color)
            holder.buttonDislike.backgroundTintList = mContext.resources.getColorStateList(R.drawable.dislike_background_color)
            holder.buttonLike.isClickable = true
            holder.buttonDislike.isClickable = false
            if(sPref.contains("${currentItem.pictureId}")) {
                if(sPref.getBoolean("${currentItem.pictureId}", false) == true){
                    editor.remove("${currentItem.pictureId}")
                    editor.commit()
                    editor.putBoolean("${currentItem.pictureId}", false)
                    editor.commit()
                }
            } else {
                editor.putBoolean(currentItem.pictureId, false)
                editor.commit()
            }

            //Делаем актуальными последние десять лайков
            configureLastTen(0, currentItem.mCatImageURL)
        }
    }

    override fun getItemCount(): Int {
        return mCatList.size
    }

    inner class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mImageView: ImageView
        val buttonLike: Button
        val buttonDislike: Button

        init {
            mImageView = itemView.findViewById(R.id.image_view)
            buttonLike = itemView.findViewById(R.id.like_button)
            buttonDislike = itemView.findViewById(R.id.dislike_button)
        }
    }

}