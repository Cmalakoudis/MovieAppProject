package com.example.moviesapplicationcm.data

import com.example.moviesapplicationcm.model.Movie
import com.example.moviesapplicationcm.model.MovieDbResponse
import com.example.moviesapplicationcm.model.MovieDetails
import kotlinx.coroutines.flow.Flow

class OfflineMoviesRepository(private val movieDao: MoviesDao) : MoviesRepository {
    override suspend fun getMoviesList(): MovieDbResponse {
        TODO()
    }

    override fun getMovie(id: Int): /*Flow<Movie?>*/ Flow<List<String>> {
        return movieDao.getMovie(id)
    }

    suspend fun insertMovie(movie: MovieItem) {
        movieDao.insertMovie(movie)
    }

    suspend fun deleteMovie(id:Int) {
        movieDao.deleteMovie(id)
    }

    suspend fun updateMovie(movie: MovieItem) {
        movieDao.updateMovie(movie)
    }
}