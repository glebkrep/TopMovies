package com.glebkrep.topmovies.API

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query



interface TheMovieDbApi {
    @GET("discover/movie/")
    fun discoverMoviesAsync(@Query("sort_by") sortBy:String = "popularity.desc",
                  @Query("language") language:String = "en-US",
                  @Query("primary_release_year")year:String = "2019",
                  @Query("page") page:Int=1): Deferred<Response<MovieResponse>>
}