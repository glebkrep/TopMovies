package com.glebkrep.topmovies

import android.app.Application
import android.content.SharedPreferences
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.glebkrep.topmovies.API.MovieModel
import com.glebkrep.topmovies.API.RetrofitClient
import com.glebkrep.topmovies.Repository.MovieItem
import com.glebkrep.topmovies.Repository.MovieItemRepository
import com.glebkrep.topmovies.Repository.MovieItemRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.*

class MainActivityViewModel(application: Application): AndroidViewModel(application) {
    private val repository: MovieItemRepository
    val allMovieItems: LiveData<List<MovieItem>>
    var pageToLoad:Int

    init {
        pageToLoad = 1
        val movieItemDao = MovieItemRoomDatabase.getDatabase(application,viewModelScope).movieItemDao()
        repository = MovieItemRepository(movieItemDao)
        allMovieItems = repository.allMovies

    }

    fun insert(movieItem: MovieItem)= viewModelScope.launch {
        repository.insert(movieItem)
    }

    fun update(id:Int, scheduledTime: Long,isActive:Boolean) = viewModelScope.launch {
        repository.update(id,scheduledTime,isActive)
    }


    //_----------------------------------------

    //TODO: Move this to shared prefs
    private var isLoading = false

    fun fetchMovies(){
        if (!isLoading){
            isLoading = true

            viewModelScope.launch(Dispatchers.Main){
                repository.fetchMovies()
            }
            pageToLoad++
            isLoading = false
        }


    }




//    private suspend fun downloadImageForUri(posterPath:String):Uri{
//        //TODO: make function
//        withContext(Dispatchers.IO){
//
//            val requestOptions = RequestOptions().override(400)
//                .downsample(DownsampleStrategy.CENTER_INSIDE)
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//
//            context
//            Glide.with()
//            mContext.get()?.let {
//                val bitmap = Glide.with(it)
//                    .asBitmap()
//                    .load(uri)
//                    .apply(requestOptions)
//                    .submit()
//                    .get()
//                try {
//
//                    var file = File(it.filesDir, "WishItemImages")
//                    if (!file.exists()) {
//                        file.mkdir()
//                    }
//                    file = File(file, "img$wishItemId.jpg")
//                    val out = FileOutputStream(file)
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 75, out)
//                    out.flush()
//                    out.close()
//                    val newUri = file.toUri()
//                    changeImage(wishItemId,newImageURI = newUri.toString())
//
//
//                    Log.i("MainActivityViewModel", "Image saved., old uri ${uri.toString()}, new uri ${newUri.toString()}")
//                } catch (e: Exception) {
//                    Log.i("MainActivityViewModel", "Failed to save image.")
//                }
//            }
//        }
//    }

}
