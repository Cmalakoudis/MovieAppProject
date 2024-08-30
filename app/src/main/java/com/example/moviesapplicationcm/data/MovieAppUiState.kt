package com.example.moviesapplicationcm.data

import com.example.moviesapplicationcm.model.Movie

data class AppUIState(
    val movieAppUiState: MovieAppUiState = MovieAppUiState(),
    val networkUiState: NetworkUiState = NetworkUiState.Loading,
    val movieListData: MovieListData = MovieListData()) {

    sealed interface NetworkUiState {
        data object Loading : NetworkUiState

        data object Error : NetworkUiState

        data object Success : NetworkUiState
    }

    data class MovieAppUiState(
        var darkTheme: Boolean = false,
        var isLoggedIn: Boolean = false,
        var viewingPopular: Boolean = true,
        var detailsPopUp: Boolean = false,
        var detailedMovie: Movie = Movie(1, "title", "description", "previewpic", "previewbackround", "releaseDate", "rating", 123, false),
    )

    data class MovieListData(
        var movieList: List<Movie> = emptyList(),
        var favouriteMovieIDs: List<Int> = emptyList(),
    )

}