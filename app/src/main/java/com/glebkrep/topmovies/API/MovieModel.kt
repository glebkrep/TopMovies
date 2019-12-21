package com.glebkrep.topmovies.API

import java.util.*

data class MovieModel(
    val id: Int,
    val vote_average: Double,
    val title: String,
    val overview: String,
    val release_date:String,
    val poster_path:String
)

// Data Model for the Response returned from the Api
data class MovieResponse(
    val results: List<MovieModel>
)

