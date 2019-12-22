package com.glebkrep.topmovies.Notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.glebkrep.topmovies.R

class NotifyWorker(context: Context,params:WorkerParameters ):Worker(context,params) {
    val mContext = context
    override fun doWork(): Result {

        triggerNotification()

        return Result.success()
    }

    fun triggerNotification(){
        val notificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "MoviesChannel"
        //If on Oreo then notification required a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "WorkManager", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(mContext, channelId)
            .setContentTitle("Single Worker")
            .setContentText("This notification is from Single Worker!!")
            .setSmallIcon(R.drawable.ic_launcher_foreground)

        notificationManager.notify(1, notification.build())

    }
}