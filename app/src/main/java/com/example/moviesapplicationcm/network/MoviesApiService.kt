package com.example.moviesapplicationcm.network

import com.example.moviesapplicationcm.model.Movie
import retrofit2.http.GET

interface MoviesApiService {
        @GET("photos")
        suspend fun getMovies(): List<Movie>
}

