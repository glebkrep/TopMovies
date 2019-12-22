package com.glebkrep.topmovies.Repository

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "movie_table")
data class MovieItem(
    @PrimaryKey
    @ColumnInfo(name="id")
    val id: Int,
    @ColumnInfo(name="vote_average")
    val vote_average: Double,
    @ColumnInfo(name="title")
    val title: String,
    @ColumnInfo(name="overview")
    val overview: String,
    @ColumnInfo(name="release_date")
    val release_date:String,
    @ColumnInfo(name="poster_path")
    val poster_path:Uri,
    @ColumnInfo(name="popularity")
    val popularity:Double,
    @ColumnInfo(name="scheduled_time")
    val scheduledTime:Date? = null,
    @ColumnInfo(name="is_active")
    val isActive:Boolean = false
)