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
        var isLoggedIn: Boolean = false,
        var viewingPopular: Boolean = true,
        var userName:String = "123",
        var detailsPopUp: Boolean = false,
        var profilePopUp: Boolean = false,
        var detailedMovieId: Int? =null,
        var page: Int = 0,
        var expandedSorting:Boolean = false,
        var sortingValue:String = "Default",
        var searchQuery:String = "",
        var viewingSearched: Boolean = false,
    )

    data class MovieListData(
        var movieList: List<Movie> = emptyList(),
        var favouriteMovieIDs: List<Int> = emptyList(),
        var favouriteMovieDisplay: List<Movie> = emptyList(),
        var queryMovieDisplay: List<Movie> = emptyList(),
    )

}

data class UserPreferencesClass(
    val darkTheme: Boolean = false,
    val isLoggedIn: Boolean = true,
    val userName:String = "",
)