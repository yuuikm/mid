package com.example.mid.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.mid.utils.AlarmUtils
import com.example.mid.utils.NotificationsUtils


class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        Log.v("ALARM", "WAKE UP")

        NotificationsUtils.lanzarNotificacion(
            context,
            title = "Alarm ${ intent.extras?.getString(AlarmUtils.Extras.TIME) } !"
        )

    }

}