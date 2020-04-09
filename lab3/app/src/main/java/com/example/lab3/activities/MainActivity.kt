package com.example.lab3.activities

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lab3.R
import com.example.lab3.data.UniversityContract
import com.example.lab3.data.UniversityDbHelper
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView : TextView = findViewById(R.id.dbversion)
        textView.text = "DB version: ${UniversityDbHelper.DATABASE_VERSION}"
    }

    fun onFirstClicked(view  : View){
        val intent : Intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }

    fun onSecondClicked(view: View){
        val intent : Intent = Intent(this, AddNewLineActivity::class.java)
        startActivity(intent)
    }

    fun onThirdClicked(view : View){
        var maxPos : Int = -1
        val COLUMS : Array<String>
        val mDbHelper = UniversityDbHelper(this)
        val db: SQLiteDatabase = mDbHelper.getWritableDatabase()

        if(UniversityDbHelper.DATABASE_VERSION == 1) {
            COLUMS = arrayOf<String>(
                UniversityContract.Students._ID,
                UniversityContract.Students.COLUMN_NAME,
                UniversityContract.Students.COLUMN_DATE
            )
        } else {
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

        while(cursor.moveToNext()){
            if(cursor.isLast){
                maxPos = cursor.getInt(cursor.getColumnIndex(UniversityContract.Students._ID))
            }
        }

        if(maxPos == -1){
            Toast.makeText(this, "В таблице нет студентов!", Toast.LENGTH_SHORT).show()
        } else {
            val cv : ContentValues = ContentValues()
            if(UniversityDbHelper.DATABASE_VERSION == 1) {
                cv.put(UniversityContract.Students.COLUMN_NAME, "Иванов Иван Иванович")
            }
            else {
                cv.put(UniversityContract.Students.COLUMN_SURNAME, "Иванов")
                cv.put(UniversityContract.Students.COLUMN_NAME, "Иван")
                cv.put(UniversityContract.Students.COLUMN_LAST_NAME, "Иванович")
            }
            db.update(UniversityContract.Students.TABLE_NAME,
                cv,
                UniversityContract.Students._ID + "= ?", arrayOf<String>(maxPos.toString()))
            Toast.makeText(this, "Иванов Иван Иванович на масте!", Toast.LENGTH_SHORT).show()
        }
    }

    fun onUpradeClicked(view : View){
        if(UniversityDbHelper.DATABASE_VERSION == 1) {
            UniversityDbHelper.DATABASE_VERSION++
        } else
            Toast.makeText(this, "Already upgraded!", Toast.LENGTH_SHORT).show()
        val textView : TextView = findViewById(R.id.dbversion)
        textView.text="DB version: ${UniversityDbHelper.DATABASE_VERSION}"
    }

    fun onDowngradeClicked(view : View){
        if(UniversityDbHelper.DATABASE_VERSION == 2){
            UniversityDbHelper.DATABASE_VERSION--
        } else
            Toast.makeText(this, "Already downgraded!", Toast.LENGTH_SHORT).show()
        val textView : TextView = findViewById(R.id.dbversion)
        textView.text="DB version: ${UniversityDbHelper.DATABASE_VERSION}"
    }
}
