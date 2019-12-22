package com.glebkrep.topmovies.Repository

import androidx.lifecycle.LiveData
import java.util.*

class MovieItemRepository(private val movieItemDao: MovieItemDao){

    val allMovies: LiveData<List<MovieItem>> = movieItemDao.getMoviesForList()
    val scheduledMovies: LiveData<List<MovieItem>> = movieItemDao.getScheduledMovies()

    suspend fun insert(movieItem: MovieItem){
        movieItemDao.insert(movieItem)
    }

    suspend fun update(id:Int,scheduledTime:Date,isActive:Boolean){
        movieItemDao.updateScheduledTimeAndActive(id,scheduledTime,isActive)
    }


    suspend fun fetchMovies(){

    }
}