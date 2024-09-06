package com.example.moviesapplicationcm.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviesapplicationcm.R
import com.example.moviesapplicationcm.data.AppUIState
import com.example.moviesapplicationcm.model.Movie
import com.example.moviesapplicationcm.ui.MovieViewModel
import com.example.moviesapplicationcm.ui.theme.MoviesApplicationCMTheme

@Composable
fun MovieListScreen(myViewModel: MovieViewModel, onDetailsPressed: () -> Unit) {
    val uiState by myViewModel.uiState.collectAsState()
    val detailsPanel = uiState.movieAppUiState.detailsPopUp
    MoviesApplicationCMTheme(darkTheme = uiState.movieAppUiState.darkTheme) {
        Box(modifier = Modifier.fillMaxSize()) {
            BasicScreenLayout(
                screenContent = { MovieListComposable(
                    uiState = uiState,
                    onCardClicked = { movie -> myViewModel.onPressedCard(movie) },
                    onFavouriteClick = { movie -> myViewModel.makeFavourite(movie) },
                    retryAction = { myViewModel.getMovieList()}
                    ) },
                myViewModel = myViewModel,
            )
            if (detailsPanel) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val movie = uiState.movieListData.movieList.find { it.id == uiState.movieAppUiState.detailedMovieId }
                    if(movie != null) {
                        MovieDetailsPopUp(
                            movieId = movie.id,
                            uiState = uiState,
                            makeFavourite = myViewModel::makeFavourite,
                            onDetailsPressed = onDetailsPressed,
                            onDismiss = { myViewModel.closePopUp() }
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun MovieListComposable(uiState: AppUIState,
                             onCardClicked: (movie: Movie) -> Unit,
                             onFavouriteClick: (movie: Movie) -> Boolean,
                             retryAction: () -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        when(uiState.networkUiState) {
            AppUIState.NetworkUiState.Loading -> {
                item {
                    LoadingScreen()
                }
            }
            AppUIState.NetworkUiState.Error -> {
                item {
                    ErrorScreen(retryAction = retryAction)
                }
            }
            AppUIState.NetworkUiState.Success -> {
                if(uiState.movieAppUiState.viewingPopular) {
                    items(uiState.movieListData.movieList.size) { movieIndex ->
                        val movie = uiState.movieListData.movieList[movieIndex]
                        MovieCard(movieId = movie.id,
                            onCardClicked =  onCardClicked,
                            onFavouriteClick = onFavouriteClick,
                            uiState = uiState
                            )
                    }
                }
                else {
                    items(uiState.movieListData.favouriteMovieIDs.size) { movieIndex ->
                        val movie = uiState.movieListData.movieList.find { it.id == uiState.movieListData.favouriteMovieIDs[movieIndex] }!!
                        MovieCard(movieId = movie.id,
                            onCardClicked =  onCardClicked,
                            onFavouriteClick = onFavouriteClick,
                            uiState = uiState
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MovieListComposableContent(networkUiState:Int = 2, viewingPopular:Boolean = true, movieList: List<Movie> ){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        when(networkUiState) {
            0 -> {
                item {
                    LoadingScreen()
                }
            }
            1 -> {
                item {
                    ErrorScreen(retryAction = {})
                }
            }
            2 -> {
                if(viewingPopular) {
                    items(movieList.size) { movieIndex ->
                        val movie = movieList[movieIndex]
                        MovieCardContent(movie = movie)
                    }
                }
                else {
                    items(movieList.size) {
                        val movie = movieList.find { it.isFavourite }!!
                        MovieCardContent(movie)
                    }
                }
            }

            else -> {}
        }
    }
}
@Suppress("SameParameterValue")
@Composable
private fun MovieListScreenContent(darkTheme: Boolean= true, detailsPanel:Boolean = false, loginInfoPanel: Boolean = false , movieList: List<Movie>, detailedMovie: Movie) {
    MoviesApplicationCMTheme(darkTheme = darkTheme) {
        Box(modifier = Modifier.fillMaxSize()) {
            BasicScreenLayoutContent( profilePopUp = loginInfoPanel,
                screenContent = { MovieListComposableContent(movieList = movieList, viewingPopular = true) },
            )
            if (detailsPanel) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MovieDetailsPopUpContent(detailedMovie)
                }
            }
        }
    }
}

@Composable
private fun LoadingScreen() {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
        CircularProgressIndicator(
            modifier = Modifier.size(100.dp),
            color = MaterialTheme.colorScheme.tertiary,
            trackColor = MaterialTheme.colorScheme.secondary,
        )
    }
}

@Composable
private fun ErrorScreen(retryAction: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "Error occurred. Retrying...", color = MaterialTheme.colorScheme.error)
        Icon(painter = painterResource(id = R.drawable.baseline_error_outline_24)
            , contentDescription = "Error occurred",
            tint = MaterialTheme.colorScheme.error)
    }
    retryAction()
}

@Preview
@Composable
fun PreviewMovieListScreen(detailsPanel: Boolean = false, loginInfoPanel:Boolean = false) {
    val movieList = listOf(Movie(1, stringResource(R.string.movie_title),  stringResource(R.string.movie_discription), R.drawable.previewpic.toString(), R.drawable.previewbackround.toString(), stringResource(R.string.release_date),stringResource(R.string.rating), 1000, false,"Action", 1000, 125 ),
        Movie(1, stringResource(R.string.movie_title),  stringResource(R.string.movie_discription), R.drawable.previewpic.toString(), R.drawable.previewbackround.toString(), stringResource(R.string.release_date),stringResource(R.string.rating), 1000, false,"Action", 1000, 125 ),
        Movie(1, stringResource(R.string.movie_title),  stringResource(R.string.movie_discription), R.drawable.previewpic.toString(), R.drawable.previewbackround.toString(), stringResource(R.string.release_date),stringResource(R.string.rating), 1000, false,"Action", 1000, 125 ),
        Movie(1, stringResource(R.string.movie_title),  stringResource(R.string.movie_discription), R.drawable.previewpic.toString(), R.drawable.previewbackround.toString(), stringResource(R.string.release_date),stringResource(R.string.rating), 1000, false,"Action", 1000, 125 ),
        Movie(1, stringResource(R.string.movie_title),  stringResource(R.string.movie_discription), R.drawable.previewpic.toString(), R.drawable.previewbackround.toString(), stringResource(R.string.release_date),stringResource(R.string.rating), 1000, false,"Action", 1000, 125 ),
        Movie(1, stringResource(R.string.movie_title),  stringResource(R.string.movie_discription), R.drawable.previewpic.toString(), R.drawable.previewbackround.toString(), stringResource(R.string.release_date),stringResource(R.string.rating), 1000, false,"Action", 1000, 125 ),
        Movie(1, stringResource(R.string.movie_title),  stringResource(R.string.movie_discription), R.drawable.previewpic.toString(), R.drawable.previewbackround.toString(), stringResource(R.string.release_date),stringResource(R.string.rating), 1000, false,"Action", 1000, 125 ),
        )

    MovieListScreenContent(darkTheme = true, detailsPanel = detailsPanel,loginInfoPanel= loginInfoPanel, movieList = movieList, detailedMovie = movieList[0])
}