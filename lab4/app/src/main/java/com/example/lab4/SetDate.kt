package com.example.lab4

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.DatePicker
import java.util.*

const val DAY_COUNT_SET = "DAY_COUNT_SET"

class SetDate : Activity() {

    companion object {

        lateinit var mDatePicker: DatePicker
        lateinit var mOkButton: Button
        lateinit var mCancelButton: Button

        var setDay = 0
        var setMonth = 0
        var setYear = 0

        var appWidgetId = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_date)

//        this.requestWindowFeature(Window.FEATURE_NO_TITLE)

        mDatePicker = findViewById(R.id.date_picker)
        mOkButton = findViewById(R.id.ok)
        mCancelButton = findViewById(R.id.cancel)

        appWidgetId = intent.getIntExtra("appWidgetId", 0)

        val today = Calendar.getInstance()

        mDatePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)) { view, year, monthOfYear, dayOfMonth ->
            setDay = dayOfMonth
            setMonth = monthOfYear
            setYear = year
        }

        val cMin = Calendar.getInstance()
        mDatePicker.minDate = cMin.timeInMillis + 1000*60*60*24

    }
//
    fun onOkClicked(view : View){
        val c1 = Calendar.getInstance()
        c1.set(setYear, setMonth, setDay)
        val dayCount = (c1.timeInMillis - Calendar.getInstance().timeInMillis)/(1000*60*60*24)

        //This intent is to set number of days
        val intentBackToWidget = Intent(this, MyWidget::class.java)
        intentBackToWidget.putExtra("DAY_COUNT", dayCount)
        intentBackToWidget.putExtra("appWidgetId", appWidgetId)
        intentBackToWidget.action = DAY_COUNT_SET
        sendBroadcast(intentBackToWidget)

        finish()
    }

    fun onCancelClicked(view : View){
        finish()
    }

}