package com.example.moviesapplicationcm.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviesapplicationcm.ui.MovieViewModel
import com.example.moviesapplicationcm.ui.theme.MoviesApplicationCMTheme

@Composable
fun BasicScreenLayout(
    modifier: Modifier = Modifier,
    screenContent: @Composable ()->Unit,
    floatingActionButton:@Composable () -> Unit = {}, //viewmodel.login
    myViewModel: MovieViewModel,
) {
    val uiState by myViewModel.uiState.collectAsState()
    Surface(color = colorScheme.primary) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                MoviesTopAppBar(
                    uiState = uiState,
                    onProfileClicked = { myViewModel.onProfileClicked() },
                    checkDark = { myViewModel.changeTheme() })
            },
            floatingActionButton = floatingActionButton,
            bottomBar = { MoviesBottomAppBar(myViewModel = myViewModel) }

        ) { paddingValues ->
            Column(
                modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalDivider(
                    modifier = modifier
                        .width(360.dp)
                        .align(Alignment.CenterHorizontally),
                    thickness = 2.dp, color = colorScheme.primary,
                )
                Spacer(modifier = modifier.weight(1f))
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    screenContent()
                    if (uiState.movieAppUiState.profilePopUp) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            LogInInfoPopUp(uiState= uiState, onDismiss = { myViewModel.closePopUp() })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BasicScreenLayoutContent(
    modifier: Modifier = Modifier,
    screenContent: @Composable ()->Unit,
    floatingActionButton:@Composable () -> Unit = {}, //viewmodel.login
    profilePopUp:Boolean = false,
) {
    Surface(color = colorScheme.primary) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                MoviesTopAppBarContent( checkButton = true)
            },
            floatingActionButton = floatingActionButton,
            bottomBar = { MoviesBottomAppBarContent() }

        ) { paddingValues ->
            Column(
                modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalDivider(
                    modifier = modifier
                        .width(360.dp)
                        .align(Alignment.CenterHorizontally),
                    thickness = 2.dp, color = colorScheme.primary,
                )
                Spacer(modifier = modifier.weight(1f))
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    screenContent()
                    if (profilePopUp) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            LogInInfoPopUpContent()
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun LayoutScreenPreview() {
    MoviesApplicationCMTheme {
        BasicScreenLayoutContent(     screenContent = { })
    }
}

@Preview
@Composable
private fun LayoutScreenPreviewDark() {
    MoviesApplicationCMTheme(darkTheme = true) {
        BasicScreenLayoutContent(     screenContent = { })
    }
}
