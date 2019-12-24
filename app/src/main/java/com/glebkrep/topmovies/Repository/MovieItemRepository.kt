package com.glebkrep.topmovies.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.impl.utils.LiveDataUtils
import com.glebkrep.topmovies.API.MovieModel
import com.glebkrep.topmovies.API.RetrofitClient
import java.lang.Exception
import java.util.*

class MovieItemRepository(private val movieItemDao: MovieItemDao){

    val allMovies: LiveData<List<MovieItem>> = movieItemDao.getMoviesForList()
    val scheduledMovies: LiveData<List<MovieItem>> = movieItemDao.getScheduledMovies()
    val troublesConnecting: MutableLiveData<Boolean> = MutableLiveData()

    suspend fun insert(movieItem: MovieItem){
        movieItemDao.insert(movieItem)
    }

    suspend fun update(id:Int,scheduledTime:Long,isActive:Boolean){
        movieItemDao.updateScheduledTimeAndActive(id,scheduledTime,isActive)
    }

    private val movieService = RetrofitClient.instanse

    suspend fun fetchMovies(){
        val page:Int = getPage()
        val request = movieService.discoverMoviesAsync(page = page)
        try {

            val response = request.await()
            val movieResponse = response.body() // single object response
            val movies = movieResponse?.results
            convertMovies(movies!!,page)

            troublesConnecting.postValue(false)
        }
        catch (e: Exception){
            e.printStackTrace()
            troublesConnecting.postValue(true)
        }
    }
    private suspend fun convertMovies(movieModels:List<MovieModel>, page:Int){
        for(movieModel in movieModels){
            var newMovie = MovieItem(id = movieModel.id,
                vote_average =  movieModel.vote_average,
                title = movieModel.title,
                overview = movieModel.overview,
                release_date = movieModel.release_date,
                poster_path = movieModel.poster_path,
                popularity = movieModel.popularity,
                page = page)

            insert(newMovie)
        }
    }

    private fun getPage():Int{
        if (!allMovies.value.isNullOrEmpty()){
            return allMovies.value!!.last().page+1
        }
        else return 1
    }

}