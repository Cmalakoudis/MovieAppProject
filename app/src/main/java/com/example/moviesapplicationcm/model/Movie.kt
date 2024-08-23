package com.example.moviesapplicationcm.model

// Generated temporary movie data class
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val runtime: Int,
    val genres: List<String>,
    val tagline: String,
    val budget: Int,
    val revenue: Int,
    val status: String,
    val homepage: String,
    val imdbId: String,
    val productionCompanies: List<String>,
    val productionCountries: List<String>,
    val spokenLanguages: List<String>,
    val isFavourite: Boolean
)
