package com.example.moviesapplicationcm.ui.screens
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviesapplicationcm.R
import com.example.moviesapplicationcm.model.Movie
import com.example.moviesapplicationcm.ui.MovieViewModel
import com.example.moviesapplicationcm.ui.MoviesViewModelProvider
import com.example.moviesapplicationcm.ui.theme.MoviesApplicationCMTheme

@Composable
fun LogInScreen(
    modifier: Modifier = Modifier,
    onKeyboardDone: () -> Unit = {}, //viewmodel.login
    onFabPressed: ()-> Unit = {},
    myViewModel: MovieViewModel
) {
    val uiState by myViewModel.uiState.collectAsState()
    MoviesApplicationCMTheme(darkTheme = uiState.movieAppUiState.darkTheme) {

        Box(modifier = Modifier.fillMaxSize()) {
            BasicScreenLayout(
                screenContent = { LogInScreenContent(myViewModel = myViewModel, onKeyboardDone = onKeyboardDone) },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { onFabPressed() },
                        containerColor = colorScheme.tertiary,
                        contentColor = colorScheme.primary
                    ) {
                        Icon(Icons.Filled.Check, stringResource(id = R.string.app_name))
                    }
                },
                myViewModel = myViewModel
            )
        }
    }
}

@Composable
fun LogInScreenContent(
    modifier: Modifier = Modifier,
    onKeyboardDone: () -> Unit = {}, //viewmodel.login
    myViewModel: MovieViewModel
) {
    val uiState by myViewModel.uiState.collectAsState()
    Column(
            modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val mediumPadding = 16.dp
            HorizontalDivider(
                modifier = modifier
                    .width(360.dp)
                    .align(Alignment.CenterHorizontally),
                thickness = 2.dp,
                color = colorScheme.primary,
            )
            Spacer(modifier = modifier.weight(1f))
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(mediumPadding),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                shape = shapes.medium,
                colors = CardColors(
                    containerColor = colorScheme.surface,
                    contentColor = colorScheme.onSurface,
                    disabledContentColor = colorScheme.primary,
                    disabledContainerColor = colorScheme.onPrimary
                )
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(mediumPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(mediumPadding)
                ) {
                    Text(
                        text = stringResource(id = R.string.login),
                        style = typography.displayMedium,
                        color = colorScheme.onSurface,
                    )
                    Text(
                        text = stringResource(id = R.string.insert_username),
                        textAlign = TextAlign.Center,
                        style = typography.titleMedium,
                        color = colorScheme.onSurface,
                    )
                    OutlinedTextField(
                        value = uiState.movieAppUiState.userName,
                        singleLine = true,
                        shape = shapes.large,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorScheme.surface,
                            unfocusedContainerColor = colorScheme.primary,
                            disabledContainerColor = colorScheme.onSurface,
                        ),
                        onValueChange ={ myViewModel.updateUserName(it)},
                        label = {
                            Text(
                                text = stringResource(id = R.string.username_example),
                                style = typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                color = colorScheme.onSurface
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { onKeyboardDone() }
                        )
                    )
                }
            }
            Spacer(modifier = modifier.weight(1f))

        }
    }




//@Preview
//@Composable
//fun LogInScreenPreview() {
//    LogInScreen(
//        onNameChange = {},
//        onKeyboardDone = {},
//        userName = "username"
//    )
//}



//        // Check if the task is saved and call onTaskUpdate event
//        LaunchedEffect(uiState.isTaskSaved) {
//            if (uiState.isTaskSaved) {
//                onTaskUpdate()
//            }
//        }
