package com.example.moviesapplicationcm.data

import com.example.moviesapplicationcm.network.MoviesApiService
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.converter.gson.GsonConverterFactory
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.moviesapplicationcm.data.UserPreferencesRepository

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val moviesRepository: MoviesRepository
    val offlineMoviesRepository: OfflineMoviesRepository
    val userPreferencesRepository: UserPreferencesRepository
}
/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
private const val LAYOUT_PREFERENCE_NAME = "layout_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = LAYOUT_PREFERENCE_NAME
)
class DefaultAppContainer(private val context: Context) : AppContainer {
    private val baseUrl = "https://api.themoviedb.org/3/"

    private fun create(): MoviesApiService {
        val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }


        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApiService::class.java)
    }


    /**
     * DI implementation for Movie photos repository
     */
    override val moviesRepository: MoviesRepository by lazy {
        NetworkMoviesRepository(create())
    }

    override val offlineMoviesRepository: OfflineMoviesRepository by lazy {
        OfflineMoviesRepository(MoviesDataBase.getDatabase(context).moviesDao())
    }

    override val userPreferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepository(context.dataStore)
    }
}
