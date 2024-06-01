package com.example.myapplication.compare

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.concurrent.TimeUnit

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NotificationApp() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val calculateRequest = PeriodicWorkRequestBuilder<CalculateWork>(24, TimeUnit.HOURS)
        .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
        .build()


      val channelId = "MyTestChannel"

    val postNotificationPermission =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    LaunchedEffect(Unit) {
        if (!postNotificationPermission.status.isGranted) {
            postNotificationPermission.launchPermissionRequest()
        }
    }

   createNotificationChannel(channelId, context)

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "calculateWork",
        ExistingPeriodicWorkPolicy.REPLACE,
        calculateRequest
    )

}

fun createNotificationChannel(channelId: String, context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "MyTestChannel"
        val descriptionText = "My important test channel"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }

        with(NotificationManagerCompat.from(context)) {
            createNotificationChannel(channel)
        }
    //        val notificationManager: NotificationManager =
    //            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    //        notificationManager.createNotificationChannel(channel)
    //   }
    }
}

class UserActiveReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("doWork()", "onReceive호출, intent.action is ${intent.action}")
        if (intent.action == Intent.ACTION_USER_PRESENT) {
            val calendar = Calendar.getInstance()
            //if (calendar.get(Calendar.HOUR_OF_DAY) >= 8) {  // 오전 8시 이후 체크
            showSimpleNotificationWithTapAction(
                context,
                "MyTestChannel", // 알림 채널 ID 확인 필요
                1,          // 알림 ID
                "Notification Title",  // 알림 제목
                "This notification opens an activity on tap."  // 알림 내용
            )
            //}
        }
    }
}

// shows a simple notification with a tap action to show an activity
fun showSimpleNotificationWithTapAction(
    context: Context,
    channelId: String,
    notificationId: Int,
    textTitle: String,
    textContent: String,
    priority: Int = NotificationCompat.PRIORITY_DEFAULT
) {
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    val pendingIntent: PendingIntent =
        PendingIntent.getActivity(context, 0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.baseline_access_alarm_24)
        .setContentTitle(textTitle)
        .setContentText(textContent)
        .setPriority(priority)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("doWork()", "return에서 빠져나옴")
            return
        }
        Log.d("doWork()", "notify 직전까지 옴")
        notify(notificationId, builder.build())
    }
}



fun calculateInitialDelay(): Long {

    return 10000L  // 10초를 밀리초로 변환

//    val now = Calendar.getInstance()
//    val eightAm = Calendar.getInstance().apply {
//        set(Calendar.HOUR_OF_DAY, 8)
//        set(Calendar.MINUTE, 0)
//        set(Calendar.SECOND, 0)
//        set(Calendar.MILLISECOND, 0)
//    }
//    val tenPm = Calendar.getInstance().apply {
//        set(Calendar.HOUR_OF_DAY, 22)
//        set(Calendar.MINUTE, 0)
//        set(Calendar.SECOND, 0)
//        set(Calendar.MILLISECOND, 0)
//    }
//
//    return when {
//        now.before(eightAm) -> {
//            // 오전 8시 전이면 오전 8시까지 기다리기
//            eightAm.timeInMillis - now.timeInMillis
//        }
//        now.before(tenPm) -> {
//            // 오전 8시 이후이면서 오후 10시 전이면 오후 10시까지 기다리기
//            tenPm.timeInMillis - now.timeInMillis
//        }
//        else -> {
//            // 오후 10시 이후면 다음 날 오전 8시까지 기다리기
//            eightAm.add(Calendar.DATE, 1)
//            eightAm.timeInMillis - now.timeInMillis
//        }
//    }.also {
//        // 밀리초를 일, 시, 분, 초로 변환하여 쉽게 이해
//        val days = TimeUnit.MILLISECONDS.toDays(it)
//        val hours = TimeUnit.MILLISECONDS.toHours(it) % 24
//        val minutes = TimeUnit.MILLISECONDS.toMinutes(it) % 60
//        val seconds = TimeUnit.MILLISECONDS.toSeconds(it) % 60
//        println("Delay set for $days days, $hours hours, $minutes minutes, $seconds seconds.")
//    }
}


