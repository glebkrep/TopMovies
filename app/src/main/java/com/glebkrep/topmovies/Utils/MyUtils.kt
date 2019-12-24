package com.glebkrep.topmovies.Utils

import java.text.SimpleDateFormat
import java.util.*

object MyUtils {
    fun convertMillisToDate(millis:Long):String{
        val formatter = SimpleDateFormat("dd/MM/yyyy kk:mm")
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis
        return formatter.format(calendar.time)
    }
    fun scheduledConvert(millis: Long):String{
        return "Scheduled: "+ convertMillisToDate(millis)
    }
    fun watchedConvert(millis: Long):String{
        return "Watched: " + convertMillisToDate(millis)
    }
}