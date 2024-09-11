package com.example.moviesapplicationcm.ui.screens

import android.content.ClipData.Item
import android.util.Log
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.D
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviesapplicationcm.R
import com.example.moviesapplicationcm.data.AppUIState
import com.example.moviesapplicationcm.model.Movie
import com.example.moviesapplicationcm.ui.MovieViewModel
import com.example.moviesapplicationcm.ui.theme.MoviesApplicationCMTheme
import com.example.moviesapplicationcm.ui.theme.White
import org.openjdk.tools.javac.jvm.Items

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
                    retryAction = { myViewModel.getMovieList()},
                    onNextPageClicked = { myViewModel.getNextPage()},
                    onPreviousPageClicked = { myViewModel.getPreviousPage()},
                    onExpand = { myViewModel.expandSorting()},
                    onSortValueChanged = { myViewModel.sortMovies(it) },
                    updateSearchQuery = { myViewModel.updateSearchQuery(it) },
                    search = {myViewModel.searchMovie()},
                    stopSearch = { myViewModel.stopSearch() }
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
                                onNextPageClicked: () -> Unit,
                                onPreviousPageClicked: () -> Unit,
                             retryAction: () -> Unit,
                                onExpand: () -> Unit,
                                onSortValueChanged: (String) -> Unit,
                                updateSearchQuery:(String) -> Unit,
                                search: () -> Unit,
                                stopSearch: () -> Unit
) {

    Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {

        DynamicSelectTextField(options = listOf("Release Date", "Rating", "Runtime", "Review count", "Default"),
            selectedValue = uiState.movieAppUiState.sortingValue,
            label = "Sort By",
            onExpand ={onExpand()},
            onSortValueChanged = {onSortValueChanged(it)},
            uiState = uiState,
        )
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                when (uiState.networkUiState) {
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
                        if (uiState.movieAppUiState.viewingPopular) {
                            items(uiState.movieListData.movieList.size) { movieIndex ->
                                val movie = uiState.movieListData.movieList[movieIndex]
                                MovieCard(
                                    movieId = movie.id,
                                    onCardClicked = onCardClicked,
                                    onFavouriteClick = onFavouriteClick,
                                    uiState = uiState
                                )
                            }
                        } else if (uiState.movieAppUiState.viewingSearched) {
                            item(1){
                                TextButton(
                                    onClick = { stopSearch() },
                                    colors = ButtonColors(
                                        containerColor = MaterialTheme.colorScheme.secondary,
                                        contentColor = MaterialTheme.colorScheme.onPrimary,
                                        disabledContainerColor = White,
                                        disabledContentColor = White
                                    ),
                                    modifier = Modifier.padding(end = 16.dp).fillMaxWidth(),
                                    contentPadding = PaddingValues(0.dp),

                                    ) {
                                    Text(
                                        text = "Clear Search",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.width(78.dp),
                                        lineHeight = 17.sp,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight(400)
                                    )
                                }
                            }
                            items(uiState.movieListData.queryMovieDisplay.size) { movieIndex ->
                                val movie = uiState.movieListData.queryMovieDisplay[movieIndex]
                                MovieCard(
                                    movieId = movie.id,
                                    onCardClicked = onCardClicked,
                                    onFavouriteClick = onFavouriteClick,
                                    uiState = uiState
                                )
                            }
                        } else {
                            items(uiState.movieListData.favouriteMovieDisplay.size) { movieIndex ->
                                MovieCard(
                                    movieId = uiState.movieListData.favouriteMovieDisplay[movieIndex].id,
                                    onCardClicked = onCardClicked,
                                    onFavouriteClick = onFavouriteClick,
                                    uiState = uiState
                                )

                            }
                        }
                    }
                }
            }
            if (uiState.movieAppUiState.viewingPopular || uiState.movieAppUiState.viewingSearched) {
                Row(modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    TextButton(
                        onClick = { onPreviousPageClicked() },
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = White,
                            disabledContentColor = White
                        ),
                        modifier = Modifier.padding(start = 16.dp),
                        contentPadding = PaddingValues(0.dp),

                        ) {
                        Text(
                            text = "Previous",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(78.dp),
                            lineHeight = 17.sp,
                            fontSize = 14.sp,
                            fontWeight = FontWeight(400)
                        )
                    }

                    Spacer(Modifier.padding(8.dp))

                    OutlinedTextField(
                        value = uiState.movieAppUiState.searchQuery,
                        singleLine = true,
                        shape = shapes.small,
                        modifier = Modifier.weight(0.3f),
                        textStyle = TextStyle(
                            textAlign = TextAlign.Center,
                            lineHeight = 17.sp,
                            fontSize = 14.sp,
                            fontWeight = FontWeight(400)
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorScheme.surface,
                            unfocusedContainerColor = colorScheme.secondary,
                            disabledContainerColor = colorScheme.onPrimary,
                        ),
                        onValueChange ={ updateSearchQuery(it)},
                        label = {
                            Text(
                                text = "movie, actor, director..",
                                style = typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                color = colorScheme.onSurface
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { search() }
                        )
                    )

                    Spacer(Modifier.padding(8.dp))

                    TextButton(
                        onClick = { onNextPageClicked() },
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = White,
                            disabledContentColor = White
                        ),
                        modifier = Modifier.padding(end = 16.dp),
                        contentPadding = PaddingValues(0.dp),

                        ) {
                        Text(
                            text = "Next",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(78.dp),
                            lineHeight = 17.sp,
                            fontSize = 14.sp,
                            fontWeight = FontWeight(400)
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DynamicSelectTextField(
    selectedValue: String,
    options: List<String>,
    label: String,
    onSortValueChanged: (String) -> Unit,
    uiState: AppUIState,
    onExpand:()-> Unit,
    modifier: Modifier = Modifier
) {

    ExposedDropdownMenuBox(
        expanded = uiState.movieAppUiState.expandedSorting,
        onExpandedChange = { onExpand()},
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = {},
            label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = uiState.movieAppUiState.expandedSorting)
            },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = uiState.movieAppUiState.expandedSorting, onDismissRequest = { onExpand() }) {
            options.forEach { option: String ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        onExpand()
                        onSortValueChanged(option)
                    }
                )
            }
        }
    }
}


@Composable
private fun MovieListComposableContent(networkUiState:Int = 2, viewingPopular:Boolean = true, movieList: List<Movie> ){
    Column {

        DynamicSelectTextFieldContent(options = listOf("Release Date", "Rating", "Popularity"), selectedValue = "Popularity", label = "Sort By")
        Box(contentAlignment = Alignment.BottomCenter) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                when (networkUiState) {
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
                        if (viewingPopular) {
                            items(movieList.size) { movieIndex ->
                                val movie = movieList[movieIndex]
                                MovieCardContent(movie = movie)
                            }
                        } else {
                            items(movieList.size) {
                                val movie = movieList.find { it.isFavourite }!!
                                MovieCardContent(movie)
                            }
                        }
                    }

                    else -> {}
                }
            }
            Row(modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                TextButton(
                    onClick = { },
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = White,
                        disabledContentColor = White
                    ),
                    modifier = Modifier.padding(start = 16.dp),
                    contentPadding = PaddingValues(0.dp),

                    ) {
                    Text(
                        text = "Previous",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(78.dp),
                        lineHeight = 17.sp,
                        fontSize = 14.sp,
                        fontWeight = FontWeight(400)
                    )
                }

                Spacer(Modifier.padding(8.dp))

                OutlinedTextField(
                    value = "",
                    singleLine = true,
                    shape = shapes.small,
                    modifier = Modifier.weight(0.3f),
                    textStyle = TextStyle(
                        textAlign = TextAlign.Center,
                        lineHeight = 17.sp,
                        fontSize = 14.sp,
                        fontWeight = FontWeight(400)
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surface,
                        unfocusedContainerColor = colorScheme.secondary,
                        disabledContainerColor = colorScheme.onPrimary,
                    ),
                    onValueChange ={},
                    label = {
                        Text(
                            text = "movie, actor, director..",
                            style = typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = colorScheme.onSurface
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { }
                    )
                )

                Spacer(Modifier.padding(8.dp))

                TextButton(
                    onClick = { },
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = White,
                        disabledContentColor = White
                    ),
                    modifier = Modifier.padding(end = 16.dp),
                    contentPadding = PaddingValues(0.dp),

                    ) {
                    Text(
                        text = "Next",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(78.dp),
                        lineHeight = 17.sp,
                        fontSize = 14.sp,
                        fontWeight = FontWeight(400)
                    )
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DynamicSelectTextFieldContent(
    modifier: Modifier = Modifier,
    selectedValue: String,
    options: List<String>,
    label: String,
    expanded:Boolean = false,
) {

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {},
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = {},
            label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { }) {
            options.forEach { option: String ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                    }
                )
            }
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

@Preview
@Composable
private fun PreviewMovieListScreenWhite(detailsPanel: Boolean = false, loginInfoPanel:Boolean = false) {
    val movieList = listOf(Movie(1, stringResource(R.string.movie_title),  stringResource(R.string.movie_discription), R.drawable.previewpic.toString(), R.drawable.previewbackround.toString(), stringResource(R.string.release_date),stringResource(R.string.rating), 1000, false,"Action", 1000, 125 ),
        Movie(1, stringResource(R.string.movie_title),  stringResource(R.string.movie_discription), R.drawable.previewpic.toString(), R.drawable.previewbackround.toString(), stringResource(R.string.release_date),stringResource(R.string.rating), 1000, false,"Action", 1000, 125 ),
        Movie(1, stringResource(R.string.movie_title),  stringResource(R.string.movie_discription), R.drawable.previewpic.toString(), R.drawable.previewbackround.toString(), stringResource(R.string.release_date),stringResource(R.string.rating), 1000, false,"Action", 1000, 125 ),
        Movie(1, stringResource(R.string.movie_title),  stringResource(R.string.movie_discription), R.drawable.previewpic.toString(), R.drawable.previewbackround.toString(), stringResource(R.string.release_date),stringResource(R.string.rating), 1000, false,"Action", 1000, 125 ),
        Movie(1, stringResource(R.string.movie_title),  stringResource(R.string.movie_discription), R.drawable.previewpic.toString(), R.drawable.previewbackround.toString(), stringResource(R.string.release_date),stringResource(R.string.rating), 1000, false,"Action", 1000, 125 ),
        Movie(1, stringResource(R.string.movie_title),  stringResource(R.string.movie_discription), R.drawable.previewpic.toString(), R.drawable.previewbackround.toString(), stringResource(R.string.release_date),stringResource(R.string.rating), 1000, false,"Action", 1000, 125 ),
        Movie(1, stringResource(R.string.movie_title),  stringResource(R.string.movie_discription), R.drawable.previewpic.toString(), R.drawable.previewbackround.toString(), stringResource(R.string.release_date),stringResource(R.string.rating), 1000, false,"Action", 1000, 125 ),
    )

    MovieListScreenContent(darkTheme = false, detailsPanel = detailsPanel,loginInfoPanel= loginInfoPanel, movieList = movieList, detailedMovie = movieList[0])
}