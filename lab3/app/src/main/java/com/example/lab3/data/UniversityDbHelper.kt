package com.example.lab3.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.view.View
import android.widget.TextView
import org.w3c.dom.Text

class UniversityDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {
        val DATABASE_NAME = "university.db"
        var DATABASE_VERSION = 2
    }

    override fun onCreate(db: SQLiteDatabase) {
        val SQL_CREATE_STUDENTS_TABLE = "CREATE TABLE ${UniversityContract.Students.TABLE_NAME}" +
                "(${UniversityContract.Students._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${UniversityContract.Students.COLUMN_SURNAME} TEXT NOT NULL, " +
                "${UniversityContract.Students.COLUMN_NAME} TEXT NOT NULL, " +
                "${UniversityContract.Students.COLUMN_LAST_NAME} TEXT NOT NULL, " +
                "${UniversityContract.Students.COLUMN_DATE} TEXT NOT NULL);"
        db.execSQL(SQL_CREATE_STUDENTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if(oldVersion<2 && newVersion == 2) {
            db.execSQL("ALTER TABLE students RENAME TO students_old")
            db.execSQL("CREATE TABLE ${UniversityContract.Students.TABLE_NAME} " +
                    "(${UniversityContract.Students._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "${UniversityContract.Students.COLUMN_SURNAME} TEXT NOT NULL, " +
                    "${UniversityContract.Students.COLUMN_NAME} TEXT NOT NULL, " +
                    "${UniversityContract.Students.COLUMN_LAST_NAME} TEXT NOT NULL, " +
                    "${UniversityContract.Students.COLUMN_DATE} TEXT NOT NULL);")

            val COLUMS = arrayOf<String>(UniversityContract.Students._ID,
                UniversityContract.Students.COLUMN_NAME,
                UniversityContract.Students.COLUMN_DATE)

            val cursor : Cursor = db.query(
                "students_old",
                COLUMS,
                null,
                null,
                null,
                null,
                null)

            try{
                val nameColumIndex : Int = cursor.getColumnIndex(UniversityContract.Students.COLUMN_NAME)
                val dateColumIndex : Int = cursor.getColumnIndex(UniversityContract.Students.COLUMN_DATE)

                while(cursor.moveToNext()){
                    val nameFromOld : String = cursor.getString(nameColumIndex)
                    val dateFromOld : String = cursor.getString(dateColumIndex)

                    val surName = nameFromOld.substringBefore(' ')
                    val lastName = nameFromOld.substringAfterLast(' ')
                    val name = nameFromOld.substringAfter(' ').removeSuffix(lastName)

                    Log.d("checking", "$surName $name $lastName $dateFromOld")

                    val cv : ContentValues = ContentValues()
                    cv.put(UniversityContract.Students.COLUMN_SURNAME, surName)
                    cv.put(UniversityContract.Students.COLUMN_NAME, name)
                    cv.put(UniversityContract.Students.COLUMN_LAST_NAME, lastName)
                    cv.put(UniversityContract.Students.COLUMN_DATE, dateFromOld)
                    db.insert(UniversityContract.Students.TABLE_NAME, null, cv)
                }
            }
            finally {
                cursor.close()
                db.execSQL("DROP TABLE students_old")
            }
        }
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
      //  super.onDowngrade(db, oldVersion, newVersion)
        if(oldVersion>1 && newVersion == 1){
            db.execSQL("ALTER TABLE students RENAME TO students_old")
            db.execSQL("CREATE TABLE ${UniversityContract.Students.TABLE_NAME} " +
                    "(${UniversityContract.Students._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "${UniversityContract.Students.COLUMN_NAME} TEXT NOT NULL, " +
                    "${UniversityContract.Students.COLUMN_DATE} TEXT NOT NULL);")

            val COLUMS = arrayOf<String>(UniversityContract.Students._ID,
                UniversityContract.Students.COLUMN_SURNAME,
                UniversityContract.Students.COLUMN_NAME,
                UniversityContract.Students.COLUMN_LAST_NAME,
                UniversityContract.Students.COLUMN_DATE)

            val cursor : Cursor = db.query(
                "students_old",
                COLUMS,
                null,
                null,
                null,
                null,
                null)

            try{
                val surNameColumIndex : Int = cursor.getColumnIndex(UniversityContract.Students.COLUMN_SURNAME)
                val nameColumIndex : Int = cursor.getColumnIndex(UniversityContract.Students.COLUMN_NAME)
                val lastNameColumIndex : Int = cursor.getColumnIndex(UniversityContract.Students.COLUMN_LAST_NAME)
                val dateColumIndex : Int = cursor.getColumnIndex(UniversityContract.Students.COLUMN_DATE)

                while(cursor.moveToNext()){
                    val nameFromOld : String = "${cursor.getString(surNameColumIndex)} ${cursor.getString(nameColumIndex)} ${cursor.getString(lastNameColumIndex)}"
                    val dateFromOld : String = cursor.getString(dateColumIndex)

                    val cv : ContentValues = ContentValues()
                    cv.put(UniversityContract.Students.COLUMN_NAME, nameFromOld)
                    cv.put(UniversityContract.Students.COLUMN_DATE, dateFromOld)
                    db.insert(UniversityContract.Students.TABLE_NAME, null, cv)
                }
            }
            finally {
                cursor.close()
                db.execSQL("DROP TABLE students_old")
            }
        }
    }
}