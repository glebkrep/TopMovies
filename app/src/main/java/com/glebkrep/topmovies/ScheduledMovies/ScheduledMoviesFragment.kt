package com.glebkrep.topmovies.ScheduledMovies


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.glebkrep.topmovies.API.MovieModel
import com.glebkrep.topmovies.R
import com.glebkrep.topmovies.Repository.MovieItem
import kotlinx.android.synthetic.main.fragment_scheduled_movies.*

/**
 * A simple [Fragment] subclass.
 */
class ScheduledMoviesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scheduled_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments?.containsKey("movieItem") ?: false){
            val newMovie = arguments!!["movieItem"] as MovieItem
            Toast.makeText(context,newMovie.title,Toast.LENGTH_SHORT).show()
            //TODO: add movie to scheduled
        }

        val adapter = ScheduledMoviesAdapter(context,this)
        scheduledMoviesRecyclerView.layoutManager = LinearLayoutManager(context)
        scheduledMoviesRecyclerView.adapter = adapter






    }
}
