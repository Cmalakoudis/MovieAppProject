package com.example.moviesapplicationcm.model

import com.example.moviesapplicationcm.R
import com.google.gson.annotations.SerializedName

// Generated temporary movie data class
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val backRoundPath: String,
    val releaseDate: String,
    val rating: String,
    val ratingVotes: Int,
    val isFavourite: Boolean = false,
)
data class MovieDbResponse(
    @field:SerializedName("page") var page: Int,
    @field:SerializedName("results") var movies: List<MovieDetails>,
    )

data class MovieDetails(
    @field:SerializedName("adult") var adult: Boolean,
    @field:SerializedName("backdrop_path") var backRoundPath: String,
    @field:SerializedName("genre_ids") var genreIds: List<Int>,
    @field:SerializedName("id") var id: Int,
    @field:SerializedName("original_language") var originalLanguage: String,
    @field:SerializedName("original_title") var originalTitle: String,
    @field:SerializedName("overview") var overview: String,
    @field:SerializedName("popularity") var popularity: Double,
    @field:SerializedName("poster_path") var posterPath: String,
    @field:SerializedName("release_date") var releaseDate: String,
    @field:SerializedName("title") var title: String,
    @field:SerializedName("video") var video: Boolean,
    @field:SerializedName("vote_average") var rating: Double,
    @field:SerializedName("vote_count") var ratingVotes: Int
    )