package com.example.mid.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mid.R

const val CHANNEL_ID = "c_alarm"
const val CHANNEL_NAME= "Alarm notifications"
const val CHANNEL_DESCRIPCION = "Alarm notifications"

class NotificationsUtils {

    companion object {

        fun lanzarNotificacion(contexto: Context, title: String) {

            crearCanalDeNotificacions(contexto)

            val sonido = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val builder = NotificationCompat.Builder(contexto, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setContentTitle(title)
                .setSound(sonido)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            val notiManager = NotificationManagerCompat.from(contexto)

            notiManager.notify(222, builder.build())
        }

        fun crearCanalDeNotificacions(contexto: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val descriptionText = CHANNEL_DESCRIPCION
                val importance = NotificationManager.IMPORTANCE_HIGH

                val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                    description = descriptionText
                }

                val notificationManager: NotificationManager = contexto.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

}