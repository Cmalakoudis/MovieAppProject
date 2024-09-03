package com.example.moviesapplicationcm.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.moviesapplicationcm.R
import com.example.moviesapplicationcm.data.AppUIState
import com.example.moviesapplicationcm.model.Movie
import com.example.moviesapplicationcm.ui.theme.MoviesApplicationCMTheme


@Composable
fun MovieCard(
    uiState: AppUIState,
    movieId: Int,
    onCardClicked: (movie: Movie) -> Unit,
    onFavouriteClick: (movie: Movie) -> Boolean,
) {
    val movie = uiState.movieListData.movieList.find { it.id == movieId }
    if (movie == null) return
    Card(
        modifier = Modifier.size(width = 361.dp, height = 153.dp),
        onClick = {onCardClicked(movie)},
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
        shape = RoundedCornerShape(6.dp),) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage( colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surface, blendMode = BlendMode.Softlight, )
                , modifier = Modifier.fillMaxSize(), alpha = 0.25f
                , contentScale = ContentScale.Crop, model = movie.backRoundPath, contentDescription = null)
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {

                AsyncImage(model = movie.posterPath , contentDescription = null, modifier = Modifier
                    .width(95.dp)
                    .height(120.dp)
                    .clip(
                        RoundedCornerShape(16.dp)
                    ), contentScale = ContentScale.Crop, alignment = Alignment.Center)

                Spacer(modifier = Modifier.width(16.dp))

                Column (modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Top) {

                    Text(modifier = Modifier.height(19.dp), text = movie.title, color = MaterialTheme.colorScheme.onSurface,fontSize = 16.sp  , fontWeight = FontWeight(400), lineHeight = 19.36.sp, overflow = TextOverflow.Ellipsis)

                    Spacer( modifier = Modifier.height(6.dp))

                    Text(modifier = Modifier.height(40.dp),text = movie.overview, color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp, fontWeight = FontWeight(400), lineHeight = 14.52.sp, overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Left, )

                    Spacer( modifier = Modifier.height(6.dp))

                    Row (modifier = Modifier
                        .width(52.46.dp)
                        .height(18.dp)){
                        Icon(painter = painterResource(id = R.drawable.calendarblank), contentDescription = stringResource(id = R.string.calendar),
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurface)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = movie.releaseDate, color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp, fontWeight = FontWeight(400), lineHeight = 14.52.sp)
                    }

                    Spacer( modifier = Modifier.height(6.dp))

                    Row(modifier = Modifier
                        .width(218.dp)
                        .height(24.dp)) {
                        Row(modifier = Modifier
                            .width(188.dp)
                            .height(16.dp), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
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
                        HeartButton(movieId = movie.id, uiState = uiState , isDarkTheme = uiState.movieAppUiState.darkTheme, onCLick = onFavouriteClick )
                    }
    
    
                }
            }
        }
    }
}

@Composable
fun HeartButton(uiState: AppUIState,movieId:Int, isDarkTheme: Boolean, onCLick: (movie: Movie) -> Boolean) {
    val movie = uiState.movieListData.movieList.find { it.id == movieId }
    if (movie == null) return
    val favourite = movie.isFavourite
    val heartPainter = if (favourite) {
        painterResource(id = R.drawable.heart_full)
    } else {
        painterResource(id = R.drawable.heart_off)
    }
    val heartStringResource = if (favourite) {
        stringResource(id = R.string.favourite)
    } else {
        stringResource(id = R.string.not_favourite)
    }
    val heartTint = if (favourite || isDarkTheme) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onPrimary
    }

    IconButton(onClick ={onCLick(movie)
                        } ,modifier = Modifier.size(16.dp),) {
        Icon(painter = heartPainter, contentDescription = heartStringResource ,
            tint =heartTint,)
    }
}

@Composable
fun HeartButtonContent(favourite:Boolean = false, isDarkTheme: Boolean = false) {
    val heartPainter = if (favourite) {
        painterResource(id = R.drawable.heart_full)
    } else {
        painterResource(id = R.drawable.heart_off)
    }
    val heartTint = if (favourite || isDarkTheme) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onPrimary
    }

    IconButton(onClick ={
    } ,modifier = Modifier.size(16.dp),) {
        Icon(painter = heartPainter, contentDescription = null ,
            tint =heartTint,)
    }
}

@Preview
@Composable
fun MovieCardPreview() {
    val myMovie = Movie(1,
        stringResource(id = R.string.movie_title),
        stringResource(id = R.string.movie_discription),
        "","",
//        painterResource(id = R.drawable.previewpic),
//        painterResource(id = R.drawable.previewbackround),
        stringResource(id = R.string.movie_release_date),
        stringResource(id = R.string.movie_rating),
        123,
        false)
    MoviesApplicationCMTheme {
        Column {
//            MovieCard(
//                movie = myMovie,
//                onCardClicked = {},
//                isDarkTheme = false,
//                onFavouriteClick = {true}
//            )
//            MovieCard(
//                movie = myMovie,
//                onCardClicked = {},
//                isDarkTheme = true,
//                onFavouriteClick = {true}
//            )
        }
    }

}