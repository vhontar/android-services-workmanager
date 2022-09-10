package com.vhontar.learningservices

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.vhontar.learningservices.service.*
import com.vhontar.learningservices.workmanager.SimpleWorker


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // runJobScheduler()
         runWorker()
        // runJobIntentService()
        // runIntentService()
        // runJobService()
        // runForegroundService()
        // runService()
    }

    private fun runJobScheduler() {
        val serviceComponent = ComponentName(this, SimpleJobService::class.java)
        val builder = JobInfo.Builder(0, serviceComponent)
        builder.setMinimumLatency((1 * 1000).toLong()) // wait at least
        builder.setOverrideDeadline((3 * 1000).toLong()) // maximum delay
        //builder.setPeriodic(5000)

        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        //builder.setRequiresCharging(false); // we don't care if the device is charging or not
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        //builder.setRequiresCharging(false); // we don't care if the device is charging or not
        val jobScheduler: JobScheduler = getSystemService(JobScheduler::class.java)
        jobScheduler.schedule(builder.build())
    }

    private fun runWorker() {
        val constraints = Constraints.Builder()
            .setRequiresCharging(false)
            .build()

        val saveWorkerRequest = OneTimeWorkRequestBuilder<SimpleWorker>()
            .setConstraints(constraints)
            .addTag("saveWorkerRequest")
            .build()

        val workManager = WorkManager.getInstance(application)
        workManager.beginUniqueWork(
            "SimpleWorker",
            ExistingWorkPolicy.REPLACE,
            saveWorkerRequest
        ).enqueue()
    }

    private fun runJobIntentService() {
        SimpleJobIntentService.enqueueWork(mainActivity(), Intent())
    }

    private fun runIntentService() {
        val intent = Intent(this, SimpleIntentService::class.java)
        startService(intent)
    }

    private fun runJobService() {
        val job: JobInfo = JobInfo.Builder(12334, ComponentName(this@MainActivity, SimpleJobService::class.java))
            //.setPeriodic(JobInfo.getMinPeriodMillis(), JobInfo.getMinFlexMillis())
            //.setRequiresDeviceIdle(false)
            .setRequiresCharging(false)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            //.setPersisted(true)
            .build()

        val scheduler: JobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        scheduler.schedule(job)
    }

    private fun runForegroundService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // only for newer versions
            val pushIntent = Intent(this, SimpleForegroundService::class.java)
            startForegroundService(pushIntent)
        } else {
            val pushIntent = Intent(this, SimpleForegroundService::class.java)
            startService(pushIntent)
        }
    }

    private fun runService() {
        val pushIntent = Intent(this, SimpleService::class.java)
        startService(pushIntent)
    }

    private fun mainActivity() = this
}