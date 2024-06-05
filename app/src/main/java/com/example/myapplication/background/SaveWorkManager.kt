package com.example.myapplication.background

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.myapplication.background.SaveWorker

class SaveWorkManager {
    lateinit var context: Context
    val workRequest:WorkRequest = OneTimeWorkRequestBuilder<SaveWorker>().build()

    fun enqueueWorkRequest() {
        WorkManager.getInstance(context).enqueue(workRequest)
    }
}