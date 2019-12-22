package com.glebkrep.topmovies.Repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(MovieItem::class),version = 1)
public abstract class MovieItemRoomDatabase:RoomDatabase() {

    abstract fun movieItemDao(): MovieItemDao

    companion object{
        @Volatile
        private var INSTANCE: MovieItemRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): MovieItemRoomDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
            else{
                synchronized(this){
                    val instance = Room.databaseBuilder(context.applicationContext,
                        MovieItemRoomDatabase::class.java,"movie_db").fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                    return instance
                }

            }
        }
    }
}