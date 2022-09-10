package com.vhontar.learningservices.receiver

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import com.vhontar.learningservices.service.SimpleJobIntentService
import com.vhontar.learningservices.service.SimpleJobService

/**
 *
 * */
class SimpleRestartBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context, p1: Intent?) {

        Log.d(TAG, "onReceive")

//        val intent = Intent(p0, SimpleIntentService::class.java)
//        p0.startService(intent)
        // runJobIntentService(context = p0)
        runJobService(context = p0)
    }

    private fun runJobService(context: Context) {
        val job: JobInfo = JobInfo.Builder(12334, ComponentName(context, SimpleJobService::class.java))
            //.setPeriodic(10000L)
            //.setRequiresDeviceIdle(false)
            .setRequiresCharging(false)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setPersisted(true)
            .build()

        val scheduler: JobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        scheduler.schedule(job)
    }

    private fun runJobIntentService(context: Context) {
        SimpleJobIntentService.enqueueWork(context, Intent())
    }

    companion object {
        private val TAG = "SimpleRestartBroadcastReceiver"
    }
}