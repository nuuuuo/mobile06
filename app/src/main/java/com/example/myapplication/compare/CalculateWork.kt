package com.example.myapplication.compare

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters


class CalculateWork(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        // 필요한 계산 수행
        val result = performCalculations()

        // 결과를 SharedPreferences에 저장
        val prefs = applicationContext.getSharedPreferences("AppData", Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putInt("calcResult", result)
            apply()
        }
        Log.d("doWork()", "doWork 정상 작동")
        return Result.success()
    }

    private fun performCalculations(): Int {
        // 계산 로직
        return 42  // 예시 결과
    }
}


