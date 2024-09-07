package com.example.moviesapplicationcm.ui.screens

import android.util.Log.i
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.moviesapplicationcm.R
import com.example.moviesapplicationcm.data.AppUIState
import com.example.moviesapplicationcm.model.Movie
import com.example.moviesapplicationcm.ui.theme.MoviesApplicationCMTheme
import com.example.moviesapplicationcm.ui.theme.White
import kotlin.properties.Delegates


@Composable
fun MovieDetailsPopUp(
    uiState: AppUIState,
    movieId: Int,
    onDetailsPressed: ()->Unit,
    makeFavourite: (movie: Movie) -> Boolean,
    onDismiss: () -> Unit
) {
    val movie = uiState.movieListData.movieList.find { it.id == movieId }
    if (movie == null) return
    Dialog(onDismissRequest = { onDismiss() }) {

        Card(
            modifier = Modifier.size(width = 345.dp, height = 249.dp),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.surface),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                contentColor = MaterialTheme.colorScheme.onSurface,
                disabledContentColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.onSurface
            ),
            shape = RoundedCornerShape(6.dp),
            elevation = CardDefaults.cardElevation(5.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = movie.title,
                        modifier = Modifier.width(235.dp),
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 20.sp,
                        fontWeight = FontWeight(700),
                        lineHeight = 24.2.sp
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    HeartButton(
                        movieId = movie.id,
                        uiState = uiState,
                        onCLick = { makeFavourite(movie) },
                        isDarkTheme = uiState.movieAppUiState.darkTheme
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { onDismiss() }, modifier = Modifier.size(24.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.close),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .width(313.dp)
                        .height(152.dp)
                ) {
                    AsyncImage(
                        model = movie.posterPath,
                        contentDescription = null,
                        modifier = Modifier
                            .width(120.dp)
                            .height(152.dp)
                            .clip(
                                RoundedCornerShape(16.dp)
                            ),
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Row(
                            modifier = Modifier
                                .width(188.dp)
                                .height(16.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.star),
                                contentDescription = stringResource(id = R.string.star),
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.secondary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = movie.rating,
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight(600),
                                lineHeight = 14.52.sp,
                                letterSpacing = 0.12.sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "(3232)",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 12.sp,
                                fontWeight = FontWeight(600),
                                lineHeight = 14.52.sp,
                                letterSpacing = 0.12.sp
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Row(
                                modifier = Modifier
                                    .width(52.46.dp)
                                    .height(18.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.calendarblank),
                                    contentDescription = stringResource(id = R.string.calendar),
                                    modifier = Modifier.size(16.dp),
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = movie.releaseDate,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight(400),
                                    lineHeight = 14.52.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = movie.overview,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            lineHeight = 14.52.sp,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Left,
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                TextButton(
                    onClick = { onDetailsPressed() },
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = White,
                        disabledContentColor = White
                    ),
                    modifier = Modifier
                        .width(110.dp)
                        .height(25.dp),
                    contentPadding = PaddingValues(0.dp),

                    ) {
                    Text(
                        text = stringResource(id = R.string.details),
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

@Composable
fun LogInInfoPopUp(uiState: AppUIState, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier.size(width = 345.dp, height = 108.dp),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.surface),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                contentColor = MaterialTheme.colorScheme.onSurface,
                disabledContentColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.onSurface
            ),
            shape = RoundedCornerShape(6.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Row(
                    modifier = Modifier
                        .height(26.1.dp)
                        .width(313.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_pic_emtpy),
                        contentDescription = stringResource(id = R.string.profile_picture),
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .border(
                                BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                                CircleShape
                            )
                            .size(26.1.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = uiState.movieAppUiState.userName,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(600),
                        lineHeight = 19.36.sp,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {onDismiss()},
                        modifier = Modifier.size(24.dp),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.close),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
                TextButton(
                    onClick = {/*Go to details Page */ },
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = White,
                        disabledContentColor = White
                    ),
                    modifier = Modifier
                        .width(119.dp)
                        .height(25.dp),
                    contentPadding = PaddingValues(0.dp),

                    ) {
                    Text(
                        text = stringResource(id = R.string.sign_out),
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



@Composable
fun LoggedInPopUp(uiState: AppUIState, onSignOut: () -> Unit, onProceed: () -> Unit) {
    Dialog(onDismissRequest = { }) {
        Card(
            modifier = Modifier.size(width = 345.dp, height = 150.dp),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.surface),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                contentColor = MaterialTheme.colorScheme.onSurface,
                disabledContentColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.onSurface
            ),
            shape = RoundedCornerShape(6.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "You are already logged in as:",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    lineHeight = 19.36.sp,
                )
                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
//                            modifier = Modifier.onGloballyPositioned {
//                                componentWidth = with(density) {
//                                    it.size.height
//                                }
//                            },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val resource = when(uiState.movieAppUiState.isLoggedIn) {
                            false -> R.drawable.profile_pic_emtpy
                            true -> R.drawable.profileicon
                        }
                        Image(
                            painter = painterResource(id = resource),
                            contentDescription = stringResource(id = R.string.profile_picture),
                            modifier = Modifier
                                .clip(shape = CircleShape)
                                .border(
                                    BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                                    CircleShape
                                )
                                .size(26.1.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = uiState.movieAppUiState.userName,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(600),
                            lineHeight = 19.36.sp,
                        )
                    }

                    HorizontalDivider(
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Spacer(modifier = Modifier.weight(0.25f))
                    TextButton(
                        onClick = {onSignOut() },
                        colors = ButtonColors(
                            containerColor = Color.Red,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = White,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .width(119.dp)
                            .height(25.dp),
                        contentPadding = PaddingValues(0.dp),

                        ) {
                        Text(
                            text = stringResource(id = R.string.sign_out),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(78.dp),
                            lineHeight = 17.sp,
                            fontSize = 14.sp,
                            fontWeight = FontWeight(400)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = {onProceed() },
                        colors = ButtonColors(
                            containerColor = Color.Green,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = White,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .width(119.dp)
                            .height(25.dp),
                        contentPadding = PaddingValues(0.dp),

                        ) {
                        Text(
                            text = "Proceed",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(78.dp),
                            lineHeight = 17.sp,
                            fontSize = 14.sp,
                            fontWeight = FontWeight(400)
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.25f))
                }
            }
        }
    }
}


@Composable
fun LoggedInPopUpContent(userName: String) {
    val density = LocalDensity.current
    var componentWidth by Delegates.notNull<Int>()
    Dialog(onDismissRequest = { }) {
        Card(
            modifier = Modifier.size(width = 345.dp, height = 150.dp),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.surface),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                contentColor = MaterialTheme.colorScheme.onSurface,
                disabledContentColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.onSurface
            ),
            shape = RoundedCornerShape(6.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "You are already logged in as:",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    lineHeight = 19.36.sp,
                )
                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(
//                            modifier = Modifier.onGloballyPositioned {
//                                componentWidth = with(density) {
//                                    it.size.height
//                                }
//                            },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.profile_pic_emtpy),
                                contentDescription = stringResource(id = R.string.profile_picture),
                                modifier = Modifier
                                    .clip(shape = CircleShape)
                                    .border(
                                        BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                                        CircleShape
                                    )
                                    .size(26.1.dp),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = userName,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 16.sp,
                                fontWeight = FontWeight(600),
                                lineHeight = 19.36.sp,
                            )
                        }

                    HorizontalDivider(
//                        Modifier.width(componentWidth.dp),
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Spacer(modifier = Modifier.weight(0.25f))
                    TextButton(
                        onClick = {/*Go to details Page */ },
                        colors = ButtonColors(
                            containerColor = Color.Red,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = White,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .width(119.dp)
                            .height(25.dp),
                        contentPadding = PaddingValues(0.dp),

                        ) {
                        Text(
                            text = stringResource(id = R.string.sign_out),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(78.dp),
                            lineHeight = 17.sp,
                            fontSize = 14.sp,
                            fontWeight = FontWeight(400)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = {/*Go to details Page */ },
                        colors = ButtonColors(
                            containerColor = Color.Green,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = White,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .width(119.dp)
                            .height(25.dp),
                        contentPadding = PaddingValues(0.dp),

                        ) {
                        Text(
                            text = "Proceed",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(78.dp),
                            lineHeight = 17.sp,
                            fontSize = 14.sp,
                            fontWeight = FontWeight(400)
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.25f))
                }
            }
        }
    }
}

@Composable
fun MovieDetailsPopUpContent(
    movie: Movie,
) {
    Dialog(onDismissRequest = {}) {
        Card(
            modifier = Modifier.size(width = 345.dp, height = 249.dp),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.surface),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                contentColor = MaterialTheme.colorScheme.onSurface,
                disabledContentColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.onSurface
            ),
            shape = RoundedCornerShape(6.dp),
            elevation = CardDefaults.cardElevation(5.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = movie.title,
                        modifier = Modifier.width(235.dp),
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 20.sp,
                        fontWeight = FontWeight(700),
                        lineHeight = 24.2.sp
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    HeartButtonContent()
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { }, modifier = Modifier.size(24.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.close),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .width(313.dp)
                        .height(152.dp)
                ) {
                    Image(
                        painter = painterResource(movie.posterPath.toInt()),
                        contentDescription = null,
                        modifier = Modifier
                            .width(120.dp)
                            .height(152.dp)
                            .clip(
                                RoundedCornerShape(16.dp)
                            ),
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Row(
                            modifier = Modifier
                                .width(188.dp)
                                .height(16.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.star),
                                contentDescription = stringResource(id = R.string.star),
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.secondary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = movie.rating,
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight(600),
                                lineHeight = 14.52.sp,
                                letterSpacing = 0.12.sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "(3232)",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 12.sp,
                                fontWeight = FontWeight(600),
                                lineHeight = 14.52.sp,
                                letterSpacing = 0.12.sp
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Row(
                                modifier = Modifier
                                    .width(52.46.dp)
                                    .height(18.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.calendarblank),
                                    contentDescription = stringResource(id = R.string.calendar),
                                    modifier = Modifier.size(16.dp),
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = movie.releaseDate,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight(400),
                                    lineHeight = 14.52.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = movie.overview,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            lineHeight = 14.52.sp,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Left,
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                TextButton(
                    onClick = { },
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = White,
                        disabledContentColor = White
                    ),
                    modifier = Modifier
                        .width(110.dp)
                        .height(25.dp),
                    contentPadding = PaddingValues(0.dp),

                    ) {
                    Text(
                        text = stringResource(id = R.string.details),
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

@Composable
fun LogInInfoPopUpContent(userName:String = "User Name") {
    Dialog(onDismissRequest = {}) {
        Card(
            modifier = Modifier.size(width = 345.dp, height = 108.dp),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.surface),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                contentColor = MaterialTheme.colorScheme.onSurface,
                disabledContentColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.onSurface
            ),
            shape = RoundedCornerShape(6.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier
                        .height(26.1.dp)
                        .width(313.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_pic_emtpy),
                        contentDescription = stringResource(id = R.string.profile_picture),
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .border(
                                BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                                CircleShape
                            )
                            .size(26.1.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = userName,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(600),
                        lineHeight = 19.36.sp,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(24.dp),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.close),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
                TextButton(
                    onClick = {/*Go to details Page */ },
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = White,
                        disabledContentColor = White
                    ),
                    modifier = Modifier
                        .width(119.dp)
                        .height(25.dp),
                    contentPadding = PaddingValues(0.dp),

                    ) {
                    Text(
                        text = stringResource(id = R.string.sign_out),
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

@Preview
@Composable
private fun PreviewMovieDetailsPopUp() {
    MoviesApplicationCMTheme {
        PreviewMovieListScreen(detailsPanel = true)
    }
}

@Preview
@Composable
private fun PreviewLoginInfoPopUp() {
    PreviewMovieListScreen(loginInfoPanel = true)
}


@Preview
@Composable
private fun alreadyLoggedInPreview() {
    MoviesApplicationCMTheme {
        LogInScreenContent(isLoggedIn = true)
    }
}