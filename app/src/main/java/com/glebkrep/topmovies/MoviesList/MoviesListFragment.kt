package com.glebkrep.topmovies.MoviesList


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.glebkrep.topmovies.API.RetrofitClient
import com.glebkrep.topmovies.MainActivity
import com.glebkrep.topmovies.R
import kotlinx.android.synthetic.main.fragment_movies_list.*
import javax.security.auth.callback.Callback

/**
 * A simple [Fragment] subclass.
 */
class MoviesListFragment : Fragment() {

    private lateinit var viewModel:MoviesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = MainActivity.obtainMoviesListViewModel(activity!!)
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MoviesListAdapter(context,this)
        movies_list_recycle_view.layoutManager = LinearLayoutManager(context)
        movies_list_recycle_view.adapter = adapter

        viewModel.fetchMovies()

        viewModel.popularMoviesLiveData.observe(this, Observer {
            adapter.setMoviesList(it)

        })


    }

}
