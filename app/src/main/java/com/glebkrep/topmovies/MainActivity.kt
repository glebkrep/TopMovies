package com.glebkrep.topmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*

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
        public fun obtainViewModel(activity: FragmentActivity): MainActivityViewModel {
            // Use a Factory to inject dependencies into the ViewModel
            val factory =
                ViewModelProvider.AndroidViewModelFactory.getInstance(activity.getApplication())

            return ViewModelProvider(instance!!, factory).get(MainActivityViewModel::class.java)

        }
    }

}
