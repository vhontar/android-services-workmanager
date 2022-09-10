package com.vhontar.learningservices.service

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

/**
 * JobIntentService service is an android component which helps to run some background work (while app is running)
 * Also, it restarts a service after some time when the app was killed if it wasn't finished.
 *
 * There are several important things to mention:
 *   - You don't have to manage threads by yourself, everything under the hood
 *   - It's deprecated, not advised to use
 *   - It continues running after app was killed from the beginning (needs some time to schedule service again)
 *   - Needs a permission: "android.permission.BIND_JOB_SERVICE"
 *   - Easy to run with static function: enqueueWork(context, SimpleJobIntentService::class.java, JOB_ID, intent)
 *   - If user puts background restrictions to the restricted status for the app -> nothing happens
 *   - You have to add a new <service> in AndroidManifest.xml
 *
 *   P.S. You can really make users' life miserable
 * */
class SimpleJobIntentService: JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        for (iterator in 0..9) {
            Thread.sleep(2000)
            Log.d(TAG, "onHandleWork() : $iterator")
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")

//        val filter = IntentFilter().apply {
//            addAction("com.vhontar.learningservices.serviceRestarted")
//        }
//        val recevier = SimpleRestartBroadcastReceiver()
//        registerReceiver(recevier, filter)
//
//        val broadcastIntent = Intent("com.vhontar.learningservices.serviceRestarted")
//        sendBroadcast(broadcastIntent)

        super.onDestroy()
    }

    companion object {
        private val TAG = "SimpleJobIntentService"
        private val JOB_ID = 1233
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, SimpleJobIntentService::class.java, JOB_ID, intent)
        }
    }
}