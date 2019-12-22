package com.glebkrep.topmovies.Repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.*


@Dao
interface MovieItemDao {

    @Query("select * from movie_table order by time_loaded asc")
    fun getMoviesForList(): LiveData<List<MovieItem>>

    @Insert
    suspend fun insert(movieItem: MovieItem)

    @Query("update movie_table SET scheduled_time=:scheduledTime, is_active=:isActive where id=:id")
    suspend fun updateScheduledTimeAndActive(id:Int,scheduledTime:Long,isActive:Boolean)

    @Query("delete from movie_table")
    suspend fun deleteAll()


    @Query("select * from movie_table where scheduled_time!=null order by scheduled_time desc")
    fun getScheduledMovies():LiveData<List<MovieItem>>


}