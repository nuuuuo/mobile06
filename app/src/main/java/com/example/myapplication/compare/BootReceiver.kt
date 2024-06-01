package com.example.myapplication.compare

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter


class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            // 시스템 부팅 시 필요한 설정을 여기에 구현
            registerUserActiveReceiver(context)
        }
    }

    private fun registerUserActiveReceiver(context: Context) {
        val filter = IntentFilter(Intent.ACTION_USER_PRESENT)
        context.applicationContext.registerReceiver(UserActiveReceiver(), filter)
    }
}
