package com.example.lab3.activities

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.lab3.R
import com.example.lab3.data.UniversityContract
import com.example.lab3.data.UniversityDbHelper
import java.text.SimpleDateFormat
import java.util.*

class AddNewLineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_line)
    }

    fun onAddClicked(view: View){

        var info : Long = -2L

        val mNameEdit = findViewById(R.id.editName) as EditText
        val date : Date = Date()
        val regex = Regex("""(\w+)\s(\w+)\s(\w+)""")
        val sdf : SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss")
        val formatedDate : String = sdf.format(date)

        val name : String = mNameEdit.text.toString()
        if(name.matches(regex)){
            val mDbHelper : UniversityDbHelper = UniversityDbHelper(this)
            val db : SQLiteDatabase = mDbHelper.writableDatabase

            val cv: ContentValues = ContentValues()

            if(UniversityDbHelper.DATABASE_VERSION == 1) {
                cv.put(UniversityContract.Students.COLUMN_NAME, name)
                cv.put(UniversityContract.Students.COLUMN_DATE, formatedDate)
            }
            else{
                val surName = name.substringBefore(' ')
                val lastName = name.substringAfterLast(' ')
                val newName = name.substringAfter(' ').removeSuffix(lastName)

                cv.put(UniversityContract.Students.COLUMN_SURNAME, surName)
                cv.put(UniversityContract.Students.COLUMN_NAME, newName)
                cv.put(UniversityContract.Students.COLUMN_LAST_NAME, lastName)
                cv.put(UniversityContract.Students.COLUMN_DATE, formatedDate)
            }

            info = db.insert(UniversityContract.Students.TABLE_NAME, null, cv)
        }


        when (info) {
            -1L -> {
                Toast.makeText(this, "Ошибка добавления студента!", Toast.LENGTH_SHORT).show()
            }
            -2L ->{
                Toast.makeText(this, "Введите полностью ФИО!", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this,"Добавлено!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
