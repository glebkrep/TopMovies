package com.glebkrep.topmovies.MoviesList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigator
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glebkrep.topmovies.API.MovieModel
import com.glebkrep.topmovies.R
import retrofit2.http.Url

class MoviesListAdapter internal constructor(val context: Context?, val parentFragment: Fragment) : RecyclerView.Adapter<MoviesListAdapter.MoviesListViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var moviesList = emptyList<MovieModel>()

    class MoviesListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val imageImageView: ImageView = itemView.findViewById(R.id.movieListImageImageView)
        val nameTextView: TextView = itemView.findViewById(R.id.movieListNameTextView)
        val descriptionTextView:TextView = itemView.findViewById(R.id.movieListDescriptionTextView)
        val ratingTextView:TextView = itemView.findViewById(R.id.movieListRatingTextView)
        val releaseDateTextView:TextView = itemView.findViewById(R.id.movieListReleaseDateTextView)
        val scheduleButton:Button = itemView.findViewById(R.id.movieListScheduleButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        val itemView = inflater.inflate(R.layout.movie_list_item,parent,false)
        return MoviesListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        val current = moviesList[position]
        holder.nameTextView.text = current.title
        holder.descriptionTextView.text = current.overview
        holder.ratingTextView.text = (current.vote_average*10).toString()
        holder.releaseDateTextView.text = current.release_date

        Glide.with(parentFragment)
            .load("https://image.tmdb.org/t/p/w500/"+current.poster_path)
            .placeholder(R.color.colorAccent)
            .error(R.color.colorPrimaryDark)
            .into(holder.imageImageView)

        holder.scheduleButton.setOnClickListener {
            //TODO:pass id
            val argumentsBundle : Bundle = Bundle()
            argumentsBundle.putParcelable("movieModel",current)


            findNavController(parentFragment).navigate(R.id.action_moviesListFragment_to_scheduledMoviesFragment,argumentsBundle)
        }
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