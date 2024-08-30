package com.example.moviesapplicationcm.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviesapplicationcm.model.Movie
import kotlin.coroutines.CoroutineContext

@Database(entities = arrayOf(MovieItem::class), version = 1)
    abstract class MoviesDataBase : RoomDatabase() {
        abstract fun moviesDao(): MoviesDao

        companion object {
            @Volatile
            private var INSTANCE: MoviesDataBase? = null

            fun getDatabase(context: Context): MoviesDataBase {
                return INSTANCE ?: synchronized(this) {
                    Room.databaseBuilder(
                        context,
                        MoviesDataBase::class.java,
                        "movieApp_database"
                    )
//                        .createFromAsset("database/bus_schedule.db")
                        // Wipes and rebuilds instead of    migrating if no Migration object.
                        .fallbackToDestructiveMigration()
                        .build()
                        .also {
                            INSTANCE = it
                        }
                }
            }
        }
    }
