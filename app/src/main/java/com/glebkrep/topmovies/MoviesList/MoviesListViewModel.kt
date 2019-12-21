package com.glebkrep.topmovies.MoviesList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glebkrep.topmovies.API.MovieModel
import com.glebkrep.topmovies.API.RetrofitClient
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

//TODO: SavedStateHandle
class MoviesListViewModel(): ViewModel(){

    private val movieService = RetrofitClient.instanse

    val popularMoviesLiveData = MutableLiveData<MutableList<MovieModel>>()

    fun fetchMovies(){

        viewModelScope.launch(Dispatchers.Main){
            val request = movieService.discoverMoviesAsync()
            try {

                val response = request.await()
                val movieResponse = response.body() // single object response
                val movies = movieResponse?.results
                popularMoviesLiveData.postValue(movies!!.toMutableList())
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }

    }



}