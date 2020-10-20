package com.example.mid.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import com.example.mid.activity.AlarmActivity
import com.example.mid.receivers.AlarmReceiver
import java.util.*

class AlarmUtils {
    class Extras {
        companion object {
            const val TIME = "time"
        }
    }

    companion object {

        @RequiresApi(Build.VERSION_CODES.KITKAT)
        fun habilitarAlarm(contexto: Context, calendar: Calendar, time: String, codAlarma: Int) {
            val alarmManager: AlarmManager = contexto.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(contexto, AlarmReceiver::class.java).apply {
                putExtra(AlarmActivity.Args.TIME, time)
            }

            val pendingIntent = PendingIntent.getBroadcast(contexto, codAlarma, intent, 0)

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        }

        fun deshabilidatAlarm(contexto: Context, codAlarma: Int) {
            val alarmManager = contexto.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(contexto, AlarmReceiver::class.java)

            val pendingIntent = PendingIntent.getBroadcast(contexto, codAlarma, intent, 0)

            alarmManager.cancel(pendingIntent)
        }
    }
}