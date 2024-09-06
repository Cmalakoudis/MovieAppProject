package com.example.moviesapplicationcm.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import coil.compose.AsyncImage
import com.example.moviesapplicationcm.R
import com.example.moviesapplicationcm.data.AppUIState
import com.example.moviesapplicationcm.model.Cast
import com.example.moviesapplicationcm.model.Crew
import com.example.moviesapplicationcm.model.Movie
import com.example.moviesapplicationcm.ui.MovieViewModel
import com.example.moviesapplicationcm.ui.theme.MoviesApplicationCMTheme
import com.example.moviesapplicationcm.ui.theme.White

@Composable
fun MovieDetailsScreen(myViewModel: MovieViewModel, navigateBack: () -> Unit) {
    val uiState by myViewModel.uiState.collectAsState()
    val movie = uiState.movieListData.movieList.find { it.id == uiState.movieAppUiState.detailedMovieId }
    if(movie == null)
        return
    MoviesApplicationCMTheme(darkTheme = uiState.movieAppUiState.darkTheme) {

        BasicScreenLayout(
            screenContent = { MovieDetailsSetup(uiState,{myViewModel.makeFavourite(movie)},navigateBack) },
            myViewModel = myViewModel
        )
    }
}

@Composable
private fun MovieDetailsSetup(uiState: AppUIState, onClick: (movie: Movie) -> Boolean, navigateBack: () -> Unit) {
    val movie =
        uiState.movieListData.movieList.find { it.id == uiState.movieAppUiState.detailedMovieId }
    if (movie == null) return
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.Top
    ) {
        Spacer(Modifier.height(16.dp))

        // BACKGROUND WITH TEXT AND BUTTONS
        Box(
            modifier = Modifier
                .width(393.dp)
                .height(221.06.dp)
        ) {
            AsyncImage(
                model = movie.backRoundPath,
                contentDescription = null, contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(393.dp)
                    .height(221.06.dp)
            )
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 24.dp)
                    .fillMaxWidth(), verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    IconButton(colors = IconButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContentColor = White,
                        disabledContainerColor = White
                    ),
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(32.dp)
                            .alpha(0.75f), onClick = { navigateBack() }) {
                        Image(
                            painter = painterResource(id = R.drawable.back_button2),
                            contentDescription = null
                        )
                    }

                    val heartPainter = if (movie.isFavourite) {
                        painterResource(id = R.drawable.heart_full)
                    } else {
                        painterResource(id = R.drawable.heart_off)
                    }
                    val heartStringResource = if (movie.isFavourite) {
                        stringResource(id = R.string.favourite)
                    } else {
                        stringResource(id = R.string.not_favourite)
                    }

                    IconButton(colors = IconButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContentColor = White,
                        disabledContainerColor = White
                    ),
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(32.dp)
                            .alpha(0.75f), onClick = { onClick(movie) }) {
                        Image(
                            modifier = Modifier,
                            painter = heartPainter,
                            contentDescription = heartStringResource,
                        )
                    }
                }
                Text(
                    text = movie.title,
                    fontWeight = FontWeight(700),
                    fontSize = 20.sp,
                    lineHeight = 24.2.sp
                )
            }
        }
        //FULL OVERVIEW
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
            text = movie.overview,
            fontWeight = FontWeight(500),
            fontSize = 14.sp,
            lineHeight = 16.94.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        // Duration and Genre
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {
                Text(
                    text = stringResource(id = R.string.duration),
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp,
                    lineHeight = 21.78.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = movie.runtime.toString() + "minutes",
                    fontWeight = FontWeight(500),
                    fontSize = 14.sp,
                    lineHeight = 16.94.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

            }
            Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.End) {
                Text(
                    text = stringResource(id = R.string.genre),
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp,
                    lineHeight = 21.78.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = movie.genre,
                    fontWeight = FontWeight(500),
                    fontSize = 14.sp,
                    lineHeight = 16.94.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

            }
        }
        // Rating and reviews
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {
                Text(
                    text = stringResource(id = R.string.rating),
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp,
                    lineHeight = 21.78.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = stringResource(id = R.string.star),
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = movie.rating,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight(600),
                        lineHeight = 14.52.sp
                    )
                }
            }
            Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.End) {
                Text(
                    text = stringResource(id = R.string.rating_reviews),
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp,
                    lineHeight = 21.78.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = movie.ratingVotes.toString(),
                    fontWeight = FontWeight(500),
                    fontSize = 15.sp,
                    lineHeight = 18.15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

            }
        }

        // Popularity and release date
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {
                Text(
                    text = stringResource(id = R.string.popularity),
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp,
                    lineHeight = 21.78.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = movie.popularity.toString() + "%",
                    fontWeight = FontWeight(500),
                    fontSize = 15.sp,
                    lineHeight = 18.15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

            }
            Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.End) {
                Text(
                    text = stringResource(id = R.string.release_date),
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp,
                    lineHeight = 21.78.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = movie.releaseDate,
                    fontWeight = FontWeight(500),
                    fontSize = 15.sp,
                    lineHeight = 18.15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

            }
        }

        //DIRECTOR
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.director),
                fontWeight = FontWeight(600),
                fontSize = 18.sp,
                lineHeight = 21.78.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            MovieStaff(name = movie.crew.name, imageModel = movie.crew.profilePath)
        }

        //Stars
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.stars),
                fontWeight = FontWeight(600),
                fontSize = 18.sp,
                lineHeight = 21.78.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))

            for (i in 0..<(movie.cast.size/2)) {
                Row (horizontalArrangement = Arrangement.SpaceEvenly){
                    MovieStaff(name = movie.cast[(2*i)].name, imageModel = movie.cast[2*i].profilePath)
                    Spacer(modifier = Modifier.weight(1f))
                    MovieStaff(name = movie.cast[(2*i)+1].name, imageModel = movie.cast[(2*i)+1].profilePath)
                }
            }
        }
    }
}

@Composable
private fun MovieStaff(name:String, imageModel: String) {
    Column(modifier = Modifier.width(150.dp) ,verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = imageModel, contentDescription = null, modifier = Modifier
                .clip(CircleShape)
                .size(76.dp)
                .border(
                    BorderStroke(1.dp, color = MaterialTheme.colorScheme.secondary),
                    CircleShape
                ),
            contentScale = ContentScale.Crop
        )
        Text(
            text = name,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight(500),
            fontSize = 15.sp,
            lineHeight = 18.15.sp,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
private fun MovieDetailsScreenContent(movie:Movie) {
    BasicScreenLayoutContent(
        screenContent = { MovieDetailsSetupContent(movie) },
    )
}

@Composable
private fun MovieDetailsSetupContent(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.Top
    ) {
        Spacer(Modifier.height(16.dp))

        // BACKGROUND WITH TEXT AND BUTTONS
        Box(
            modifier = Modifier
                .width(393.dp)
                .height(221.06.dp)
        ) {
            Image(
                painter = painterResource(movie.backRoundPath.toInt()),
                contentDescription = null, contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(393.dp)
                    .height(221.06.dp)
            )
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 24.dp)
                    .fillMaxWidth(), verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    IconButton(colors = IconButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContentColor = White,
                        disabledContainerColor = White
                    ),
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(32.dp)
                            .alpha(0.75f), onClick = { }) {
                        Image(
                            painter = painterResource(id = R.drawable.back_button2),
                            contentDescription = null
                        )
                    }

                    val heartPainter = if (movie.isFavourite) {
                        painterResource(id = R.drawable.heart_full)
                    } else {
                        painterResource(id = R.drawable.heart_off)
                    }
                    val heartStringResource = if (movie.isFavourite) {
                        stringResource(id = R.string.favourite)
                    } else {
                        stringResource(id = R.string.not_favourite)
                    }

                    IconButton(colors = IconButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContentColor = White,
                        disabledContainerColor = White
                    ),
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(32.dp)
                            .alpha(0.75f), onClick = {}) {
                        Image(
                            modifier = Modifier,
                            painter = heartPainter,
                            contentDescription = heartStringResource,
                        )
                    }
                }
                Text(
                    text = movie.title,
                    fontWeight = FontWeight(700),
                    fontSize = 20.sp,
                    lineHeight = 24.2.sp
                )
            }
        }
        //FULL OVERVIEW
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
            text = movie.overview,
            fontWeight = FontWeight(500),
            fontSize = 14.sp,
            lineHeight = 16.94.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        // Duration and Genre
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {
                Text(
                    text = stringResource(id = R.string.duration),
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp,
                    lineHeight = 21.78.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = movie.runtime.toString() + "minutes",
                    fontWeight = FontWeight(500),
                    fontSize = 14.sp,
                    lineHeight = 16.94.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

            }
            Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.End) {
                Text(
                    text = stringResource(id = R.string.genre),
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp,
                    lineHeight = 21.78.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = movie.genre,
                    fontWeight = FontWeight(500),
                    fontSize = 14.sp,
                    lineHeight = 16.94.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

            }
        }
        // Rating and reviews
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {
                Text(
                    text = stringResource(id = R.string.rating),
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp,
                    lineHeight = 21.78.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = stringResource(id = R.string.star),
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = movie.rating,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight(600),
                        lineHeight = 14.52.sp
                    )
                }
            }
            Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.End) {
                Text(
                    text = stringResource(id = R.string.rating_reviews),
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp,
                    lineHeight = 21.78.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = movie.ratingVotes.toString(),
                    fontWeight = FontWeight(500),
                    fontSize = 15.sp,
                    lineHeight = 18.15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

            }
        }

        // Popularity and release date
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {
                Text(
                    text = stringResource(id = R.string.popularity),
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp,
                    lineHeight = 21.78.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = movie.popularity.toString() + "%",
                    fontWeight = FontWeight(500),
                    fontSize = 15.sp,
                    lineHeight = 18.15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

            }
            Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.End) {
                Text(
                    text = stringResource(id = R.string.release_date),
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp,
                    lineHeight = 21.78.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = movie.releaseDate,
                    fontWeight = FontWeight(500),
                    fontSize = 15.sp,
                    lineHeight = 18.15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

            }
        }

        //DIRECTOR
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.director),
                fontWeight = FontWeight(600),
                fontSize = 18.sp,
                lineHeight = 21.78.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            MovieStaffContent(name = movie.crew.name, imageModel = movie.crew.profilePath.toInt())
        }

        //Stars
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.stars),
                fontWeight = FontWeight(600),
                fontSize = 18.sp,
                lineHeight = 21.78.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))

            for (i in 0..<(movie.cast.size/2)) {
                Row (horizontalArrangement = Arrangement.SpaceEvenly){
                    MovieStaffContent(name = movie.cast[(2*i)].name, imageModel = movie.cast[2*i].profilePath.toInt())
                    Spacer(modifier = Modifier.weight(1f))
                    MovieStaffContent(name = movie.cast[(2*i)+1].name, imageModel = movie.cast[(2*i)+1].profilePath.toInt())
                }
            }
        }
    }
}

@Composable
private fun MovieStaffContent(name:String, imageModel: Int) {
    Column(modifier = Modifier.width(150.dp) ,verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(imageModel), contentDescription = null, modifier = Modifier
                .clip(CircleShape)
                .size(76.dp)
                .border(
                    BorderStroke(1.dp, color = MaterialTheme.colorScheme.secondary),
                    CircleShape
                ),
            contentScale = ContentScale.Crop
        )
        Text(
            text = name,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight(500),
            fontSize = 15.sp,
            lineHeight = 18.15.sp,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Preview(heightDp = 1150)
@Composable
fun MovieDetailsPreview() {
    MoviesApplicationCMTheme {
        MovieDetailsScreenContent( Movie(1, stringResource(R.string.movie_title),
            stringResource(R.string.movie_discription),
            R.drawable.previewpic.toString(),
            R.drawable.previewbackround.toString(),
            stringResource(R.string.release_date),
            stringResource(R.string.rating),
            1000, false,
            "Action", 1000,
            125 ,
            listOf(Cast(stringResource(R.string.cast1), R.drawable.cast1.toString()),
                Cast(stringResource(R.string.cast2), R.drawable.cast2.toString()),
                Cast(stringResource(R.string.cast3), R.drawable.cast3.toString()),
                Cast(stringResource(R.string.cast4), R.drawable.cast4.toString()),
                Cast(stringResource(R.string.cast5), R.drawable.cast5.toString()),
                Cast(stringResource(R.string.cast6), R.drawable.cast6.toString()),
            ),
            Crew(stringResource(R.string.director1), R.drawable.director1.toString(), "directing")
        ))
    }
}