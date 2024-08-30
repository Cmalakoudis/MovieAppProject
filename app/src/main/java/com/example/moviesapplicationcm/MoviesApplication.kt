package com.example.moviesapplicationcm

import android.app.Application
import com.example.moviesapplicationcm.data.AppContainer
import com.example.moviesapplicationcm.data.DefaultAppContainer

class MoviesApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}
