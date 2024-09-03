package com.example.moviesapplicationcm.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.moviesapplicationcm.MoviesApplication

//    companion object {
//        val Factory: ViewModelProvider.Factory = viewModelFactory {
//            initializer {
//                val application = (this[APPLICATION_KEY] as MarsPhotosApplication)
//                val marsPhotosRepository = application.container.marsPhotosRepository
//                MarsViewModel(marsPhotosRepository = marsPhotosRepository)
//            }
//        }
//
//    }
    /**
     * Provides Factory to create instance of ViewModel for the entire Inventory app
     */
    object MoviesViewModelProvider {
        val Factory = viewModelFactory {
                // Initializer for HomeViewModel
                initializer {
                    MovieViewModel(inventoryApplication().container.offlineMoviesRepository,
                                    inventoryApplication().container.moviesRepository,
                                    inventoryApplication().container.userPreferencesRepository)
                }
            }
        }

        /**
         * Extension function to queries for [Application] object and returns an instance of
         * [InventoryApplication].
         */
        fun CreationExtras.inventoryApplication(): MoviesApplication =
            (this[AndroidViewModelFactory.APPLICATION_KEY] as MoviesApplication)