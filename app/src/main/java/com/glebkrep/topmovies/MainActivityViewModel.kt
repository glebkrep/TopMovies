package com.glebkrep.topmovies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.glebkrep.topmovies.Repository.MovieItem
import com.glebkrep.topmovies.Repository.MovieItemRepository
import com.glebkrep.topmovies.Repository.MovieItemRoomDatabase
import kotlinx.coroutines.launch
import java.util.*

class MainActivityViewModel(application: Application): AndroidViewModel(application) {
    private val repository: MovieItemRepository
    val allMovieItems: LiveData<List<MovieItem>>

    init {
        val movieItemDao = MovieItemRoomDatabase.getDatabase(application,viewModelScope).movieItemDao()
        repository = MovieItemRepository(movieItemDao)
        allMovieItems = repository.allMovies
    }

    fun insert(movieItem: MovieItem)= viewModelScope.launch {
        repository.insert(movieItem)
    }

    fun update(id:Int, scheduledTime: Date,isActive:Boolean) = viewModelScope.launch {
        repository.update(id,scheduledTime,isActive)
    }

}
