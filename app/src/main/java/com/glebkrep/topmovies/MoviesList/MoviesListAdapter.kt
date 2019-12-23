package com.glebkrep.topmovies.MoviesList

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glebkrep.topmovies.R
import com.glebkrep.topmovies.Repository.MovieItem
import com.glebkrep.topmovies.Utils.MyUtils

class MoviesListAdapter internal constructor(val context: Context?, val parentFragment: Fragment) : RecyclerView.Adapter<MoviesListAdapter.MoviesListViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var moviesList = emptyList<MovieItem>()

    class MoviesListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val imageImageView: ImageView = itemView.findViewById(R.id.movieListImageImageView)
        val nameTextView: TextView = itemView.findViewById(R.id.movieListNameTextView)
        val descriptionTextView:TextView = itemView.findViewById(R.id.movieListDescriptionTextView)
        val ratingTextView:TextView = itemView.findViewById(R.id.movieListRatingTextView)
        val releaseDateTextView:TextView = itemView.findViewById(R.id.movieListReleaseDateTextView)
        val scheduleButton:Button = itemView.findViewById(R.id.movieListScheduleButton)
        val scheduledTime:TextView = itemView.findViewById(R.id.movieScheduledTimeTextView)
        val movieCard:CardView = itemView.findViewById(R.id.movieListCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        val itemView = inflater.inflate(R.layout.movie_list_item,parent,false)
        return MoviesListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        val current = moviesList[position]
        holder.nameTextView.text = current.title
        holder.descriptionTextView.text = current.overview
        holder.ratingTextView.text = (current.vote_average*10).toInt().toString()
        holder.releaseDateTextView.text = current.release_date
        if (current.scheduledTime!=null){
            holder.scheduledTime.visibility = View.VISIBLE
            holder.scheduleButton.visibility = View.GONE
            if (current.isActive){
                holder.scheduledTime.text = "Scheduled: "+MyUtils.convertMillisToDate(current.scheduledTime!!)
                holder.movieCard.setCardBackgroundColor(Color.parseColor("#81C784"))
            }
            else{
                holder.scheduledTime.text = "Watched: "+MyUtils.convertMillisToDate(current.scheduledTime!!)
                holder.movieCard.setCardBackgroundColor(Color.parseColor("#8F8A7D7D"))
            }

            holder.scheduledTime.setOnClickListener {
                findNavController(parentFragment).navigate(R.id.action_moviesListFragment_to_scheduledMoviesFragment)
            }
        }
        else{
            holder.scheduledTime.visibility=View.GONE
            holder.scheduleButton.visibility = View.VISIBLE
            holder.movieCard.setCardBackgroundColor(Color.parseColor("#FFFFFFFF"))
        }

        Glide.with(parentFragment)
            .load("https://image.tmdb.org/t/p/w500/"+current.poster_path)
            .centerCrop()
            .placeholder(R.color.colorAccent)
            .error(R.color.colorPrimaryDark)
            .into(holder.imageImageView)

        holder.scheduleButton.setOnClickListener {
            val argumentsBundle : Bundle = Bundle()
            argumentsBundle.putParcelable("movieItem",current)


            findNavController(parentFragment).navigate(R.id.action_moviesListFragment_to_scheduledMoviesFragment,argumentsBundle)
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