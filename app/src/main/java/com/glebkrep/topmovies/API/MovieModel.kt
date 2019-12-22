package com.glebkrep.topmovies.API

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class MovieModel(
    val id: Int,
    val vote_average: Double,
    val title: String,
    val overview: String,
    val release_date:String,
    val poster_path:String,
    val popularity:Double
):Parcelable

// Data Model for the Response returned from the Api
data class MovieResponse(
    val results: List<MovieModel>
)

