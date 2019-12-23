package com.glebkrep.topmovies.ScheduledMovies

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glebkrep.topmovies.API.MovieModel
import com.glebkrep.topmovies.MoviesList.MoviesListAdapter
import com.glebkrep.topmovies.R
import com.glebkrep.topmovies.Repository.MovieItem
import com.glebkrep.topmovies.Utils.MyUtils

class ScheduledMoviesAdapter internal constructor(val context: Context?, val parentFragment: Fragment) : RecyclerView.Adapter<ScheduledMoviesAdapter.ScheduledMoviesViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var moviesList = emptyList<MovieItem>()
    private val curTime = System.currentTimeMillis()

    class ScheduledMoviesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.scheduledMovieNameTextView)
        val timeTextView: TextView = itemView.findViewById(R.id.scheduledMovieTimeTextView)
        val card :CardView  = itemView.findViewById(R.id.scheduledMovieCard)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduledMoviesViewHolder {
        val itemView = inflater.inflate(R.layout.scheduled_movie_item,parent,false)
        return ScheduledMoviesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ScheduledMoviesViewHolder, position: Int) {
        val current = moviesList[position]
        holder.nameTextView.text = current.title

        holder.timeTextView.text = MyUtils.convertMillisToDate(current.scheduledTime!!)


        if (!current.isActive){
            holder.card.setCardBackgroundColor(Color.parseColor("#8F8A7D7D"))
        }
        else{
            holder.card.setCardBackgroundColor(Color.parseColor("#81C784"))

        }
    }

    internal fun setMoviesList(moviesList:List<MovieItem>){
        this.moviesList = moviesList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }


    fun getMoviesList():List<MovieItem>{
        return moviesList
    }
}