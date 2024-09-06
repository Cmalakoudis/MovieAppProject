package com.example.moviesapplicationcm.data

import com.example.moviesapplicationcm.model.Movie

data class AppUIState(
    val movieAppUiState: MovieAppUiState = MovieAppUiState(),
    val networkUiState: NetworkUiState = NetworkUiState.Loading,
    val movieListData: MovieListData = MovieListData(),
) {
    sealed interface NetworkUiState {
        data object Loading : NetworkUiState

        data object Error : NetworkUiState

        data object Success : NetworkUiState
    }

    data class MovieAppUiState(
        var darkTheme: Boolean = false,
        var isLoggedIn: Boolean = true,
        var viewingPopular: Boolean = true,
        var userName:String = "",
        var detailsPopUp: Boolean = false,
        var profilePopUp: Boolean = false,
        var detailedMovieId: Int? =null
    )

    data class MovieListData(
        var movieList: List<Movie> = emptyList(),
        var favouriteMovieIDs: List<Int> = emptyList(),
    )

}

data class UserPreferencesClass(
    val darkTheme: Boolean = false,
    val isLoggedIn: Boolean = true,
    val userName:String = "",
)