package com.example.moviesapplicationcm.data

import com.example.moviesapplicationcm.model.Movie
import com.example.moviesapplicationcm.network.MoviesApiService

/**
 * Repository that fetch mars photos list from marsApi.
 */
interface MoviesRepository {
    /** Fetches list of MarsPhoto from marsApi */
    suspend fun getMoviesList(): List<Movie>
}

/**
 * Network Implementation of Repository that fetch mars photos list from marsApi.
 */
class NetworkMoviesRepository(
    private val moviesApiService: MoviesApiService
) : MoviesRepository {
    /** Fetches list of MarsPhoto from marsApi*/
    override suspend fun getMoviesList(): List<Movie> = moviesApiService.getMovies()
}
