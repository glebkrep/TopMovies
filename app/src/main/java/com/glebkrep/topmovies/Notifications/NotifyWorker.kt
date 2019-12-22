package com.glebkrep.topmovies.Notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.glebkrep.topmovies.MainActivity
import com.glebkrep.topmovies.R
import java.util.concurrent.TimeUnit

public class NotifyWorker(context: Context,params:WorkerParameters ):Worker(context,params) {
    val mContext = context
    override fun doWork(): Result {

        val title = inputData.getString(EXTRA_TITLE)
        val text = inputData.getString(EXTRA_TEXT)
        val id = inputData.getInt(EXTRA_ID,0)
        sendNotification(title!!,text!!,id)

        return Result.success()
    }

    fun sendNotification(title:String,text:String,id:Int){
        val intent = Intent(applicationContext,MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.putExtra(EXTRA_ID,id)

        val pendingIntent = PendingIntent.getActivity(applicationContext,0,intent,0)
        val notificationManager:NotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.O){
            val channel = NotificationChannel(id.toString(),title,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(applicationContext,id.toString())
            .setContentTitle(title)
            .setContentText(text)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)

        notificationManager.notify(id,notification.build())
    }

    companion object{
        const val EXTRA_TITLE = "title"
        const val EXTRA_TEXT = "text"
        const val EXTRA_ID = "id"

        //TODO: might be an instance bug try something close to dao thingy
        fun scheduleReminder(duration:Long,data: Data,tag:String,mContext: Context){
            val notificationWork = OneTimeWorkRequest.Builder(NotifyWorker::class.java)
                .setInitialDelay(duration,TimeUnit.MILLISECONDS)
                .addTag(tag)
                .setInputData(data)
                .build()
            val instance = WorkManager.getInstance(mContext)
            instance.enqueue(notificationWork)
        }
        fun cancelReminder(tag:String,mContext:Context){
            val instance = WorkManager.getInstance(mContext)
            instance.cancelAllWorkByTag(tag)
        }
    }

}
