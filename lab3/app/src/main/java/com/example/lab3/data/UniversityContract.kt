package com.example.lab3.data

import android.provider.BaseColumns

class UniversityContract {

    class Students : BaseColumns{
        companion object {
            val TABLE_NAME = "students"

            val _ID = BaseColumns._ID
            val COLUMN_SURNAME = "surname"
            val COLUMN_NAME = "name"
            val COLUMN_LAST_NAME = "lastname"
            val COLUMN_DATE = "date"
        }
    }
}