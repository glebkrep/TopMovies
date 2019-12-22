package com.glebkrep.topmovies.MoviesList


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.glebkrep.topmovies.MainActivity
import com.glebkrep.topmovies.MainActivityViewModel
import com.glebkrep.topmovies.R
import kotlinx.android.synthetic.main.fragment_movies_list.*


/**
 * A simple [Fragment] subclass.
 */
class MoviesListFragment : Fragment() {

    private lateinit var viewModel:MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = MainActivity.obtainViewModel(activity!!)
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MoviesListAdapter(context,this)
        movies_list_recycle_view.layoutManager = LinearLayoutManager(context)
        movies_list_recycle_view.adapter = adapter


        viewModel.allMovieItems.observe(this, Observer {
            adapter.setMoviesList(it)

        })


        movies_list_recycle_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.fetchMovies()
                }
            }
        })


    }

}
