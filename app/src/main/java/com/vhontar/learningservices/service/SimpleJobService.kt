package com.vhontar.learningservices.service

import android.annotation.SuppressLint
import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

/**
 * JobService is an android component which helps to run some background work
 * Also, it restarts a service after some time when the app was killed if it wasn't finished.
 * Similar to JobIntentService which is already deprecated
 *
 * JobScheduler is a 'helper' class for running JobServices with constraints
 * Most of the time, it's the most preferable way of running JobServices
 *
 * There are several important things to mention:
 *   - You have to manage threads by yourself in onStartJob (this method executes on your application's main thread_
 *   - It continues running after app was killed from the beginning (needs some time to schedule service again)
 *   - Needs a permission: "android.permission.BIND_JOB_SERVICE"
 *   - Easy to use with JobScheduler (a lot of settings)
 *   - You have to add a new <service> in AndroidManifest.xml
 *   - If user puts background restrictions to the restricted status for the app -> nothing happens
 *
 * */
@SuppressLint("SpecifyJobSchedulerIdRange")
class SimpleJobService: JobService() {
    override fun onStartJob(p0: JobParameters?): Boolean {
        Thread {
            for (iterator in 0..9) {
                Thread.sleep(2000)
                Log.d(TAG, "onStartJob() : $iterator")
            }

        }.start()

        return true
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy()")
        super.onDestroy()
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        Log.d(TAG, "onStopJob()")
        return false
    }

    companion object {
        private val TAG = "SimpleIntentService"
    }
}