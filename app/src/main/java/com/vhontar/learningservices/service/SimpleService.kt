package com.vhontar.learningservices.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 * Service is an android component which helps to run some background work (while app is running)
 *
 * There are several important things to mention:
 *   - You have to handle threads by yourself inside onStartCommand, otherwise the app will wait while this service finishes
 *   - Doesn't work when app is killed
 *   - OnCreate and OnDestroy were called after app is killed
 *   - You have to add a new <service> in AndroidManifest.xml
 *
 * */
class SimpleService: Service() {

    override fun onCreate() {
        super.onCreate()

        Log.d(TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        for (iterator in 0..9) {
//            Thread.sleep(1000)
//            Log.d(TAG, "onStartCommand() : $iterator")
//        }
        Thread {
            for (iterator in 0..9) {
                Thread.sleep(2000)
                Log.d(TAG, "onStartCommand() : $iterator")
            }
        }.start()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.d(TAG, "onBind")
        return null
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy")
    }

    companion object {
        private val TAG = "SimpleService"
    }
}