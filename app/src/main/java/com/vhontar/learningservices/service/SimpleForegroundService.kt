package com.vhontar.learningservices.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.vhontar.learningservices.R

/**
 * Foreground service is an android component which helps to run some foreground work (while app is killed)
 *
 * There are several important things to mention:
 *   - You have to create notification to show the user the progress of some service
 *       + cannot close notification prior to Android 13
 *       + If user puts background restrictions to the restricted status for the app -> nothing happens
 *   - Work when app is killed (but look for background restrictions)
 *   - You need to cancel notification by yourself when the service is finished
 *   - You need to manage situation when the user starts the app again to not create additional foreground service
 *       + notification in my test cases was always one, from user point of view doesn't clear that service is running two times
 *   - You need to create BroadcastReceiver for android.intent.action.BOOT_COMPLETED to start the service again
 *   - You have to manage threads otherwise it will block the app/screen while it runs
 *   - Uses permission: "android.permission.FOREGROUND_SERVICE"
 *   - You have to add a new <service> in AndroidManifest.xml
 *
 *   P.S. You can really make users' life miserable (not dying notification)
 * */
class SimpleForegroundService: Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread {
            for (iterator in 0..9) {
                Thread.sleep(2000)
                Log.d(TAG, "onStartCommand() : $iterator")
            }

            NotificationManagerCompat.from(applicationContext).cancelAll()
        }.start()

        val NOTIFICATION_CHANNEL_ID_DEFAULT = "channel Id"
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID_DEFAULT)
            .setOngoing(false).setSmallIcon(R.mipmap.ic_launcher).setPriority(Notification.PRIORITY_HIGH)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID_DEFAULT,
                NOTIFICATION_CHANNEL_ID_DEFAULT, NotificationManager.IMPORTANCE_LOW
            )
            notificationChannel.description = NOTIFICATION_CHANNEL_ID_DEFAULT
            notificationChannel.setSound(null, null)
            notificationManager.createNotificationChannel(notificationChannel)
            startForeground(1, builder.build())
        } else {
            startForeground(1, builder.build())
        }

        return super.onStartCommand(intent, flags, startId)
    }
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    companion object {
        private val TAG = "SimpleForegroundService"
        private val FOREGROUND_ID = 1333
    }
}