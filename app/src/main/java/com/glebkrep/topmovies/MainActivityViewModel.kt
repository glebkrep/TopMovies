package com.glebkrep.topmovies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.glebkrep.topmovies.Repository.MovieItem
import com.glebkrep.topmovies.Repository.MovieItemRepository
import com.glebkrep.topmovies.Repository.MovieItemRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MovieItemRepository
    val allMovieItems: LiveData<List<MovieItem>>
    val scheduledMovies: LiveData<List<MovieItem>>
    val troublesConnecting: MutableLiveData<Boolean>

    init {
        val movieItemDao =
            MovieItemRoomDatabase.getDatabase(application, viewModelScope).movieItemDao()
        repository = MovieItemRepository(movieItemDao)
        allMovieItems = repository.allMovies
        scheduledMovies = repository.scheduledMovies
        troublesConnecting = repository.troublesConnecting
    }

    fun insert(movieItem: MovieItem) = viewModelScope.launch {
        repository.insert(movieItem)
    }

    fun update(id: Int, scheduledTime: Long, isActive: Boolean) = viewModelScope.launch {
        repository.update(id, scheduledTime, isActive)
    }


    private var isLoading = false

    fun fetchMovies() {
        if (!isLoading) {
            isLoading = true

            viewModelScope.launch(Dispatchers.Main) {
                repository.fetchMovies()
            }
            isLoading = false
        }


    }

    fun start() {
        if (allMovieItems.value.isNullOrEmpty()) {
            fetchMovies()
        } else if (!scheduledMovies.value.isNullOrEmpty()) {
            val currentTime = System.currentTimeMillis()
            checkSheduled(currentTime)
        }

    }

    private fun checkSheduled(time: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            for (item in scheduledMovies.value!!) {
                if (item.isActive && item.scheduledTime!! < time) {
                    update(item.id, item.scheduledTime, false)
                }
            }
        }
    }

    fun troubleConnectingRecieved() {
        repository.troublesConnecting.postValue(false)
    }

}
