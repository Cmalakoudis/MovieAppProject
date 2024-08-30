package com.example.moviesapplicationcm.model

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
    var isFavourite: Boolean = false,
    var genre: String ="",
    var popularity: Int = 0,
    var runtime: Int = 0,
    var cast: List<Cast> = emptyList(),
    var crew: Crew = Crew("","", ""),
    )
data class MovieDbResponse(
    @field:SerializedName("page") val page: Int,
    @field:SerializedName("results") val movies: List<MovieDetails>,
    )

data class MovieDetailsResponse(
    @field:SerializedName("genres") val genres: List<Genre>,
    @field:SerializedName("runtime") val runtime: Int,
)

data class MovieDbCastResponse(
    @field:SerializedName("cast") val cast: List<Cast>,
    @field:SerializedName("crew") val crew: List<Crew>,
)
data class Cast(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("profile_path") var profilePath: String,
)
data class Crew(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("profile_path") var profilePath: String,
    @field:SerializedName("department") val department: String,
)
 data class Genre(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String,
)
data class MovieDetails(
    @field:SerializedName("adult") val adult: Boolean,
    @field:SerializedName("backdrop_path") val backRoundPath: String,
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("original_language") val originalLanguage: String,
    @field:SerializedName("original_title") val originalTitle: String,
    @field:SerializedName("overview") val overview: String,
    @field:SerializedName("popularity") val popularity: Double,
    @field:SerializedName("poster_path") val posterPath: String,
    @field:SerializedName("release_date") val releaseDate: String,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("video") val video: Boolean,
    @field:SerializedName("vote_average") val rating: Double,
    @field:SerializedName("vote_count") val ratingVotes: Int
    )