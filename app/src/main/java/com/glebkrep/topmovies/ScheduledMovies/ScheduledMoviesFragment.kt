package com.glebkrep.topmovies.ScheduledMovies


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
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
import com.glebkrep.topmovies.Utils.MyUtils
import kotlinx.android.synthetic.main.fragment_scheduled_movies.*
import java.util.*
import java.util.concurrent.TimeUnit


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
            getDateAndTime(newMovie)
        }

        val adapter = ScheduledMoviesAdapter(context,this)
        scheduledMoviesRecyclerView.layoutManager = LinearLayoutManager(context)
        scheduledMoviesRecyclerView.adapter = adapter


        viewModel.scheduledMovies.observe(this,Observer{
            adapter.setMoviesList(it)
        })

    }

    fun createWorkInputData(title:String,text:String,id:Int):Data{
        return Data.Builder()
            .putString(NotifyWorker.EXTRA_TITLE,title)
            .putString(NotifyWorker.EXTRA_TEXT,text)
            .putInt(NotifyWorker.EXTRA_ID,id)
            .build()
    }

    fun getDateAndTime(movie:MovieItem){
        val calendar = Calendar.getInstance()
        var mYear = calendar.get(Calendar.YEAR)
        var mMonth = calendar.get(Calendar.MONTH)
        var mDay = calendar.get(Calendar.DAY_OF_MONTH)
        var mHour = calendar.get(Calendar.HOUR_OF_DAY)
        var mMinute = calendar.get(Calendar.MINUTE)

        val datePickerDialog = DatePickerDialog(context!!,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                //after date picked
                mYear = year
                mMonth = monthOfYear
                mDay = dayOfMonth
                val timePickerDialog = TimePickerDialog(context!!,
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        //after time picked
                        mHour = hourOfDay
                        mMinute = minute
                        //set notif
                        addToScheduled(movie,mYear,mMonth,mDay,mHour,mMinute)
                    }, mHour, mMinute, false)
                timePickerDialog.show()
            }, mYear, mMonth, mDay)
        datePickerDialog.show()
    }
    fun addToScheduled(movie:MovieItem,year:Int,month:Int,day:Int,hour:Int,minute:Int){
        //Scheduling notification
        var curTime = System.currentTimeMillis()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR,year)
        calendar.set(Calendar.MONTH,month)
        calendar.set(Calendar.DAY_OF_MONTH,day)
        calendar.set(Calendar.HOUR_OF_DAY,hour)
        calendar.set(Calendar.MINUTE,minute)
        var scheduledTime = calendar.timeInMillis
        val alertTime = scheduledTime - curTime

        val tag = (movie.id+scheduledTime).toString()

        if (alertTime<0){
            Toast.makeText(context!!,"wrong time, try again",Toast.LENGTH_LONG).show()
        }
        else{
            if (movie.scheduledTime!=null){
                NotifyWorker.cancelReminder(tag,context!!)
            }
            viewModel.update(movie.id,scheduledTime,true)
            val data:Data = createWorkInputData(movie.title,getString(R.string.notificationText),movie.id)
            NotifyWorker.scheduleReminder(alertTime,data,tag,context!!)
        }

    }

}
