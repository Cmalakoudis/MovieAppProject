package com.example.moviesapplicationcm.ui.screens

import android.text.style.BackgroundColorSpan
import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Scaffold
import com.example.moviesapplicationcm.R
import com.example.moviesapplicationcm.data.AppUIState
import com.example.moviesapplicationcm.model.Movie
import com.example.moviesapplicationcm.ui.MovieViewModel
import com.example.moviesapplicationcm.ui.MoviesViewModelProvider
import com.example.moviesapplicationcm.ui.theme.MoviesApplicationCMTheme
import com.example.moviesapplicationcm.ui.theme.White

@Composable
fun MovieListContent(uiState: AppUIState.MovieListData, myViewModel: MovieViewModel) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp) , contentPadding = PaddingValues(16.dp)) {
        items(uiState.movieList.size) { movie ->
            MovieCard(uiState.movieList[movie],myViewModel )
        }
    }
}

@Composable
fun MovieListScreen (myViewModel: MovieViewModel, onDetailsPressed: ()->Unit) {
    val uiState by myViewModel.uiState.collectAsState()
    val detailsPanel = uiState.movieAppUiState.detailsPopUp
    val loginInfoPanel = false
    MoviesApplicationCMTheme(darkTheme = uiState.movieAppUiState.darkTheme) {
        Box(modifier = Modifier.fillMaxSize()) {
            BasicScreenLayout(
                screenContent = { MovieListContent(uiState.movieListData, myViewModel) },
                topBarTitle = R.string.app_name,
                onNameChange = { /*TODO*/ },
                userName = "",
                myViewModel = myViewModel,
            )
            val myMovie = Movie(
                1,
                stringResource(id = R.string.movie_title),
                stringResource(id = R.string.movie_discription),
                "R.drawable.previewpic",
                "R.drawable.previewbackround",
                stringResource(id = R.string.movie_release_date),
                stringResource(id = R.string.movie_rating),
                123,
                false
            )
            if (detailsPanel) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MovieDetailsPopUp(uiState.movieAppUiState.detailedMovie, myViewModel, onDetailsPressed = onDetailsPressed)
                }
            } else if (loginInfoPanel) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LogInInfoPopUp(movie = myMovie)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMovieListScreen() {
//    MovieListScreen()
}