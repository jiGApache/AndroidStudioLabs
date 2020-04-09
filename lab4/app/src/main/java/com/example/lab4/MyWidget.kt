package com.example.lab4

import android.app.*
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*

const val WIDGET_SYNC = "WIDGET_SYNC"
const val WIDGET_TIME_ENDED = "WIDGET_TIME_ENDED"

class MyWidget : AppWidgetProvider() {

    companion object {
        val mapOfCurrDay = mutableMapOf<Int, Long>()
        val mapOfDateWasSet = hashMapOf<Int, Boolean>()
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        Log.d("fff", "onEnabled")
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        Log.d("fff", "onDisabled")
        mapOfCurrDay.clear()
        mapOfDateWasSet.clear()
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        Log.d("fff", "onDeleted")
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {

        for (appWidgetId in appWidgetIds) {
            if(!mapOfDateWasSet.containsKey(appWidgetId)){
                mapOfDateWasSet[appWidgetId] = false
                mapOfCurrDay[appWidgetId] = 1
            }

            //This one is for asking activity to set required date and get number of days
            if(!mapOfDateWasSet[appWidgetId]!!){
                val intent = Intent(context, SetDate::class.java)
                intent.putExtra("appWidgetId", appWidgetId)
                val pendingIntent = PendingIntent.getActivity(context, appWidgetId, intent, 0)

                val views = RemoteViews(context.packageName, R.layout.my_widget)
                views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent)
                appWidgetManager.updateAppWidget(appWidgetId, views)
            }

            //This one is to update widget when it become 0:00
            else if(mapOfCurrDay[appWidgetId]!! > 0){
                val intent = Intent(context, MyWidget::class.java)
                intent.action = WIDGET_SYNC
                intent.putExtra("appWidgetId", appWidgetId)
                val pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT)

                var currTime : Long = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)*60*60*1000L +
                        Calendar.getInstance().get(Calendar.MINUTE)*60*1000L +
                        Calendar.getInstance().get(Calendar.SECOND)*1000L
                val ALARM_DELAY_IN_MSECOND = 24*60*60*1000 - currTime
                val alarmTimeAtUTC = System.currentTimeMillis() + ALARM_DELAY_IN_MSECOND

                val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                am.set(AlarmManager.RTC_WAKEUP, alarmTimeAtUTC, pendingIntent)
            }
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    override fun onReceive(context: Context, intent: Intent) {

        val appWidgetId : Int  = intent.getIntExtra("appWidgetId", -5)

        if(WIDGET_SYNC == intent.action){
            mapOfCurrDay[appWidgetId] = mapOfCurrDay[appWidgetId]!! - 1
            if(mapOfCurrDay[appWidgetId] == 0L){
                val intentLastUpdate = Intent(context, MyWidget::class.java)
                intentLastUpdate.action = WIDGET_TIME_ENDED
                intentLastUpdate.putExtra("appWidgetId", appWidgetId)
                val pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intentLastUpdate, PendingIntent.FLAG_UPDATE_CURRENT)

                val ALARM_DELAY_IN_MSECOND = 9*60*60*1000L
                val alarmTimeAtUTC = System.currentTimeMillis() + ALARM_DELAY_IN_MSECOND

                val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                am.set(AlarmManager.RTC_WAKEUP, alarmTimeAtUTC, pendingIntent)
            }
            updateAppWidget(context, AppWidgetManager.getInstance(context), appWidgetId)
        }
        else if (WIDGET_TIME_ENDED == intent.action){
            val message : MutableList<String> = mutableListOf("Time ended", "Notification message")
            sendNotification(context, AppWidgetManager.getInstance(context), appWidgetId, message)
        }
        else if (DAY_COUNT_SET == intent.action){
            mapOfDateWasSet[appWidgetId] = true
            mapOfCurrDay[appWidgetId] = intent.getLongExtra("DAY_COUNT", -5L)

            updateAppWidget(context, AppWidgetManager.getInstance(context), appWidgetId)

            //This intent is to update text in widget when it become 0:00
            val intentSync = Intent(context, MyWidget::class.java)
            intentSync.action = WIDGET_SYNC
            intentSync.putExtra("appWidgetId", appWidgetId)
            val pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intentSync, PendingIntent.FLAG_UPDATE_CURRENT)

            var currTime : Long = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)*60*60*1000L +
                    Calendar.getInstance().get(Calendar.MINUTE)*60*1000L +
                    Calendar.getInstance().get(Calendar.SECOND)*1000L
            val ALARM_DELAY_IN_MSECOND = 24*60*60*1000 - currTime
            val alarmTimeAtUTC = System.currentTimeMillis() + ALARM_DELAY_IN_MSECOND

            val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            am.set(AlarmManager.RTC_WAKEUP, alarmTimeAtUTC, pendingIntent)
        }

        super.onReceive(context, intent)

    }

    fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int){
        val views = RemoteViews(context.packageName, R.layout.my_widget)
        views.setTextViewText(R.id.appwidget_text, (mapOfCurrDay[appWidgetId]).toString())
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    fun sendNotification(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int, message : MutableList<String>){
        val views = RemoteViews(context.packageName, R.layout.my_widget)
        views.setTextViewText(R.id.appwidget_text, message[0])

        val builder = NotificationCompat.Builder(context!!, "1")
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("Notification")
            .setContentText(message[1])
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Main channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("1", name, importance)
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        val nm = NotificationManagerCompat.from(context)
        nm.notify(appWidgetId, builder.build())

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}