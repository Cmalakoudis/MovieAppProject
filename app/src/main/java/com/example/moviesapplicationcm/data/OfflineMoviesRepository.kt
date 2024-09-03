package com.example.moviesapplicationcm.data

import com.example.moviesapplicationcm.model.Movie
import com.example.moviesapplicationcm.model.MovieDbCastResponse
import com.example.moviesapplicationcm.model.MovieDbResponse
import com.example.moviesapplicationcm.model.MovieDetails
import com.example.moviesapplicationcm.model.MovieDetailsResponse
import kotlinx.coroutines.flow.Flow

class OfflineMoviesRepository(private val movieDao: MoviesDao) {
    suspend fun getMoviesList(userName: String): Flow<List<Int>> {
        return (movieDao.getMoviesList(userName))
    }

    suspend fun insertMovie(movie: MovieItem) {
        movieDao.insertMovie(movie)
    }

    suspend fun getMovieItem(userName: String): Flow<MovieItem> {
        return movieDao.getMovieItem(userName)
    }

    suspend fun deleteMovie(id:Int) {
        movieDao.deleteMovie(id)
    }

    suspend fun updateMovie(movie: MovieItem) {
        movieDao.updateMovie(movie)
    }
}