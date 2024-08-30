package com.example.moviesapplicationcm.data
import com.example.moviesapplicationcm.network.MoviesApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val moviesRepository: MoviesRepository
    val offlineMoviesRepository: OfflineMoviesRepository
}
/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
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
//            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
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
}
