package com.vhontar.learningservices.service

import android.app.IntentService
import android.content.Intent
import android.util.Log

/**
 * Intent service is an android component which helps to run some background work (while app is running)
 *
 * There are several important things to mention:
 *   - You don't have to manage threads by yourself, everything under the hood
 *   - It's deprecated, not advised to use
 *   - It doesn't run when app is killed
 *   - You have to add a new <service> in AndroidManifest.xml
 *
 *   Service's advantage -> You don't have to manage threads
 *
 *   P.S. You can really make users' life miserable
 * */
class SimpleIntentService: IntentService("SimpleIntentService") {
    override fun onHandleIntent(p0: Intent?) {
        for (iterator in 0..9) {
            Thread.sleep(2000)
            Log.d(TAG, "onHandleIntent() : $iterator")
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        stopSelf()
        val broadcastIntent = Intent("com.vhontar.learningservices.serviceRestarted")
        applicationContext.sendBroadcast(broadcastIntent)

        super.onDestroy()
    }

    companion object {
        private val TAG = "SimpleIntentService"
    }
}