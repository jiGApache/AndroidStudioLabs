package com.example.lab3.activities

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3.R
import com.example.lab3.adapters.MyNewAdapter
import com.example.lab3.adapters.MyOldAdapter
import com.example.lab3.data.UniversityContract
import com.example.lab3.data.UniversityDbHelper

class SecondActivity : AppCompatActivity() {

    var ids : ArrayList<Int> = ArrayList()
    var surnames : ArrayList<String> = ArrayList()
    var names : ArrayList<String> = ArrayList()
    var lastnames : ArrayList<String> = ArrayList()
    var dates : ArrayList<String> = ArrayList()
    val mDbHelper = UniversityDbHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        getDatabaseInfo()

        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        if(UniversityDbHelper.DATABASE_VERSION == 1)
            recyclerView.adapter = MyOldAdapter(ids, names, dates)
        else if(UniversityDbHelper.DATABASE_VERSION == 2)
            recyclerView.adapter = MyNewAdapter(ids, surnames, names, lastnames, dates)
    }

    fun getDatabaseInfo(){
        val db : SQLiteDatabase = mDbHelper.getReadableDatabase()
        val COLUMS : Array<String>

        ids.clear()
        surnames.clear()
        names.clear()
        lastnames.clear()
        dates.clear()

        if(UniversityDbHelper.DATABASE_VERSION == 1) {
            COLUMS = arrayOf<String>(
                UniversityContract.Students._ID,
                UniversityContract.Students.COLUMN_NAME,
                UniversityContract.Students.COLUMN_DATE
            )
        } else{
            COLUMS = arrayOf<String>(
                UniversityContract.Students._ID,
                UniversityContract.Students.COLUMN_SURNAME,
                UniversityContract.Students.COLUMN_NAME,
                UniversityContract.Students.COLUMN_LAST_NAME,
                UniversityContract.Students.COLUMN_DATE
            )
        }

        val cursor : Cursor = db.query(
            UniversityContract.Students.TABLE_NAME,
            COLUMS,
            null,
            null,
            null,
            null,
            null)

        try{
            if(UniversityDbHelper.DATABASE_VERSION == 1) {
                val idColumIndex: Int = cursor.getColumnIndex(UniversityContract.Students._ID)
                val nameColumIndex: Int = cursor.getColumnIndex(UniversityContract.Students.COLUMN_NAME)
                val dateColumIndex: Int = cursor.getColumnIndex(UniversityContract.Students.COLUMN_DATE)

                while (cursor.moveToNext()) {
                    val currId: Int = cursor.getInt(idColumIndex)
                    val currName: String = cursor.getString(nameColumIndex)
                    val currDate: String = cursor.getString(dateColumIndex)

                    ids.add(currId)
                    names.add(currName)
                    dates.add(currDate)
                }
            }
            else {
                val idColumIndex : Int = cursor.getColumnIndex(UniversityContract.Students._ID)
                val surnameColumIndex : Int = cursor.getColumnIndex(UniversityContract.Students.COLUMN_SURNAME)
                val nameColumIndex : Int = cursor.getColumnIndex(UniversityContract.Students.COLUMN_NAME)
                val lastnameColumIndex : Int = cursor.getColumnIndex(UniversityContract.Students.COLUMN_LAST_NAME)
                val dateColumIndex : Int = cursor.getColumnIndex(UniversityContract.Students.COLUMN_DATE)

                while (cursor.moveToNext()) {
                    val currId : Int = cursor.getInt(idColumIndex)
                    val currSurname : String = cursor.getString(surnameColumIndex)
                    val currName : String = cursor.getString(nameColumIndex)
                    val currLastname : String = cursor.getString(lastnameColumIndex)
                    val currDate : String = cursor.getString(dateColumIndex)

                    ids.add(currId)
                    surnames.add(currSurname)
                    names.add(currName)
                    lastnames.add(currLastname)
                    dates.add(currDate)
                }
            }
        }
        finally {
            cursor.close()
        }
    }
}
