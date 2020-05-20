package com.example.lab5

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context)
    : SQLiteOpenHelper(context, "catInfoDB", null, 1){
    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(("create table catImagesUrl(" +
                    "id integer primary key autoincrement," +
                    "imageUrl text" +
                    ");"))
    }

    fun insertImageUrl(imageUrl: String){
        val record = ContentValues()
        with(record){
            put("imageUrl", imageUrl)
        }
        writableDatabase.insert("catImagesUrl", null, record)
    }

    fun getImageUrls() : ArrayList<String>{
        val urls = arrayListOf<String>()
        val request = writableDatabase.query("catImagesUrl", null, null, null, null, null, "id DESC")
        if(request.moveToFirst()){
            val idUrlIdx = request.getColumnIndex("imageUrl")
            do{
                urls.add(request.getString(idUrlIdx))
            } while (request.moveToNext())
        } else {
            request.close()
        }
        return urls
    }

    fun deleteFirstUrl(){
        val deleted = writableDatabase.delete("catImagesUrl", "id = (SELECT MIN(id) FROM catImagesUrl )", null)
    }

    fun deleteImageByUrl(imageUrl: String){
        val deleted = writableDatabase.delete("catImagesUrl", "imageUrl = '$imageUrl'", null)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

}