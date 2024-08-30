package com.example.moviesapplicationcm.data

import com.example.moviesapplicationcm.model.Movie
import com.example.moviesapplicationcm.model.MovieDbCastResponse
import com.example.moviesapplicationcm.model.MovieDbResponse
import com.example.moviesapplicationcm.model.MovieDetails
import com.example.moviesapplicationcm.model.MovieDetailsResponse
import com.example.moviesapplicationcm.network.MoviesApiService
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Repository that fetch mars photos list from marsApi.
 */
interface MoviesRepository {
    /** Fetches list of movies from moviesApi */
    suspend fun getMoviesList(): MovieDbResponse
    /**
     * Retrieve an movie from the given data source that matches with the [id].
     */
    suspend fun getMovieDetails(id: Int): MovieDetailsResponse

    suspend fun getCastDetails(id: Int): MovieDbCastResponse

}

/**
 * Network Implementation of Repository that fetch movies list from moviesApi.
 */
class NetworkMoviesRepository(
    private val moviesApiService: MoviesApiService
) : MoviesRepository {

    private val authToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5N2M3YmJlYTViMDA0N2M4NjQ5ZWFkNWU2ZTkxZTEzNiIsIm5iZiI6MTcyNDg1MDcxMy45OTgyNDksInN1YiI6IjY2Y2NjMmEyNjI4NzUxNGJkNTJkN2JmMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Plih039xFRNPZL8JVlZcntGExrTWq0rXiayJPmiNej8"
    override suspend fun getMoviesList(): MovieDbResponse = moviesApiService.getMovies(authToken = authToken)

    override suspend fun getMovieDetails(id: Int): MovieDetailsResponse = moviesApiService.getMovieDetails(authToken = authToken, movieId = id)

    override suspend fun getCastDetails(id: Int): MovieDbCastResponse = moviesApiService.getCastDetails(authToken = authToken, movieId = id)
}
