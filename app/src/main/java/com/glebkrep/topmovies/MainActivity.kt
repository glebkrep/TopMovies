package com.glebkrep.topmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.glebkrep.topmovies.MoviesList.MoviesListViewModel

class MainActivity : AppCompatActivity() {
    init {
        instance = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    companion object {
        private var instance: MainActivity? = null
        public fun obtainMoviesListViewModel(activity: FragmentActivity): MoviesListViewModel {
            // Use a Factory to inject dependencies into the ViewModel
            val factory =
                ViewModelProvider.AndroidViewModelFactory.getInstance(activity.getApplication())

            return ViewModelProvider(instance!!, factory).get(MoviesListViewModel::class.java)

        }
    }
}
