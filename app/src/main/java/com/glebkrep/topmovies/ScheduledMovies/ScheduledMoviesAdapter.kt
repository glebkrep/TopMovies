package com.glebkrep.topmovies.ScheduledMovies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glebkrep.topmovies.API.MovieModel
import com.glebkrep.topmovies.MoviesList.MoviesListAdapter
import com.glebkrep.topmovies.R

class ScheduledMoviesAdapter internal constructor(val context: Context?, val parentFragment: Fragment) : RecyclerView.Adapter<ScheduledMoviesAdapter.ScheduledMoviesViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var moviesList = emptyList<MovieModel>()

    class ScheduledMoviesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageImageView: ImageView = itemView.findViewById(R.id.movieListImageImageView)
        val nameTextView: TextView = itemView.findViewById(R.id.movieListNameTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.movieListDescriptionTextView)
        val ratingTextView: TextView = itemView.findViewById(R.id.movieListRatingTextView)
        val releaseDateTextView: TextView = itemView.findViewById(R.id.movieListReleaseDateTextView)
        val scheduleButton: Button = itemView.findViewById(R.id.movieListScheduleButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduledMoviesViewHolder {
        val itemView = inflater.inflate(R.layout.movie_list_item,parent,false)
        return ScheduledMoviesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ScheduledMoviesViewHolder, position: Int) {
        val current = moviesList[position]


    }

    internal fun setMoviesList(moviesList:List<MovieModel>){
        this.moviesList = moviesList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }


    fun getMoviesList():List<MovieModel>{
        return moviesList
    }
}