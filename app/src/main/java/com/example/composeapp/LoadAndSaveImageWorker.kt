package com.example.composeapp

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadAndSaveImageWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend  fun doWork(): Result {

        withContext(Dispatchers.IO) {
        val link = inputData.getString("link") ?: return@withContext Result.failure()

            val bitmap = ImageOperations.loadImage(link) ?: return@withContext Result.failure()
            val filename = "${System.currentTimeMillis()}.jpg"
            ImageOperations.savePhoto(applicationContext, filename, bitmap)
        }

        return Result.success()
    }
}