package com.vhontar.learningservices.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

private val TAG = "SimpleWorker"

/**
 * Worker extends ListenableWorker which helps to run some background work
 * Also, it restarts a work after some time when the app was killed if it wasn't finished.
 *
 * There are several important things to mention:
 *   - You don't have to handle threads
 *   - Restart the work after the app was killed
 *   - If user puts background restrictions to the restricted status for the app -> nothing happens
 *   - Rich API: WorkManager, Constraints, Builders (OneTimeWorkRequestBuilder, PeriodicWorkRequestBuilder)
 *
 * */
class SimpleWorker(ctx: Context, params: WorkerParameters): Worker(ctx, params) {
    override fun doWork(): Result {
        for (iterator in 0..9) {
            Thread.sleep(2000)
            Log.d(TAG, "doWork() : $iterator")
        }

        return Result.success()
    }
}