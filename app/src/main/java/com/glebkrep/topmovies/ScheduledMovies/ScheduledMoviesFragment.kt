package com.glebkrep.topmovies.ScheduledMovies


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.glebkrep.topmovies.API.MovieModel
import com.glebkrep.topmovies.MainActivity
import com.glebkrep.topmovies.MainActivityViewModel
import com.glebkrep.topmovies.Notifications.NotifyWorker
import com.glebkrep.topmovies.R
import com.glebkrep.topmovies.Repository.MovieItem
import kotlinx.android.synthetic.main.fragment_scheduled_movies.*
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 */
class ScheduledMoviesFragment : Fragment() {

    private lateinit var viewModel:MainActivityViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = MainActivity.obtainViewModel(activity!!)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scheduled_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments?.containsKey("movieItem") ?: false){
            val newMovie = arguments!!["movieItem"] as MovieItem
            Toast.makeText(context,newMovie.title,Toast.LENGTH_SHORT).show()
            addToScheduled(newMovie)
        }

        val adapter = ScheduledMoviesAdapter(context,this)
        scheduledMoviesRecyclerView.layoutManager = LinearLayoutManager(context)
        scheduledMoviesRecyclerView.adapter = adapter


        viewModel.scheduledMovies.observe(this,Observer{
            adapter.setMoviesList(it)
        })



    }

    fun addToScheduled(movie:MovieItem){
        //todo: add

        //TODO:?
        //Scheduling notification
        val tag = UUID.randomUUID().toString()
        //get time before alar
        val alertTime = 1000L
        //--
        val data:Data = createWorkInputData(movie.title,"You were planning to watch this movie",movie.id)
        NotifyWorker.scheduleReminder(alertTime,data,tag,context!!)
    }

    fun createWorkInputData(title:String,text:String,id:Int):Data{
        return Data.Builder()
            .putString(NotifyWorker.EXTRA_TITLE,title)
            .putString(NotifyWorker.EXTRA_TEXT,text)
            .putInt(NotifyWorker.EXTRA_ID,id)
            .build()
    }
}
