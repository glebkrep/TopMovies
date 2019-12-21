package com.glebkrep.topmovies.API


import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object RetrofitClient {


    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "ad01326516c128ab12be57034ae74b1f"

    private val authInterceptor = Interceptor{
        val newUrl = it.request().url()
            .newBuilder()
                //adding api key to every request
            .addQueryParameter("api_key",API_KEY)
            .build()

        val newRequest = it.request()
            .newBuilder()
            .url(newUrl)
            .build()

        it.proceed(newRequest)
    }

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .build()



    val instanse:TheMovieDbApi by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        retrofit.create(TheMovieDbApi::class.java)
    }
}