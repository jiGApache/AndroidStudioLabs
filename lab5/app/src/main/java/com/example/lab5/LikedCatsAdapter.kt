package com.example.lab5

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class LikedCatsAdapter(context: Context, catList: ArrayList<String>) :
    RecyclerView.Adapter<LikedCatsAdapter.LikedCatViewHolder>() {
    private val mContext: Context
    private val mLikedCatsList: ArrayList<String>

    init {
        mContext = context
        mLikedCatsList = catList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedCatViewHolder {
        val v: View = LayoutInflater.from(mContext).inflate(R.layout.cat_item, parent, false)
        return LikedCatViewHolder(v)
    }

    override fun onBindViewHolder(holder: LikedCatViewHolder, position: Int) {
        val imageURL: String = mLikedCatsList[position]
        //Ставим картинку
        Picasso.with(mContext).load(imageURL).fit().centerInside().into(holder.mImageView)

        //Прячем кнопки оценки
        holder.buttonLike.visibility = View.GONE
        holder.buttonDislike.visibility = View.GONE
    }

    override fun getItemCount(): Int {
        return mLikedCatsList.size
    }

    inner class LikedCatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

