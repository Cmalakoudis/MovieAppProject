package com.example.moviesapplicationcm.ui.screens
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.ContentAlpha
import com.example.moviesapplicationcm.R
import com.example.moviesapplicationcm.ui.MovieViewModel

@Composable
fun LogInScreen(
    @StringRes topBarTitle: Int,
    onNameChange: (String) -> Unit, // viewmodel.updateusername
    onKeyboardDone: () -> Unit, //viewmodel.login
    userName: String,
    modifier: Modifier = Modifier,
    myViewModel: MovieViewModel = viewModel(),
) {
    Surface(color = colorScheme.primary){
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = { MoviesTopAppBar(canNavigateBack = false, navigateUp = { /*TODO*/ }) },
            floatingActionButton = {
                FloatingActionButton(onClick = {}) {
                    Icon(Icons.Filled.Done, stringResource(id = R.string.app_name))
                }
            }
        ) { paddingValues ->
             Box(
                 modifier
                     .fillMaxSize()
                     .padding(paddingValues), contentAlignment = Alignment.Center) {
                 val mediumPadding = 16.dp
                 Card(
                     modifier = modifier
                         .fillMaxWidth()
                         .wrapContentHeight()
                         .padding(mediumPadding),
                     elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                        shape = shapes.medium,
                 ) {
                     Column(
                         verticalArrangement = Arrangement.spacedBy(mediumPadding),
                         horizontalAlignment = Alignment.CenterHorizontally,
                         modifier = Modifier.padding(mediumPadding)
                     ) {
                         OutlinedCard(onClick = { /*TODO*/ }) {
                             Text(
                                 text = "Log in",
                                 style = typography.displayMedium,
                             )

                         }
                         Text(
                             text = "Please insert your username",
                             textAlign = TextAlign.Center,
                             style = typography.titleMedium
                         )
                         OutlinedTextField(
                             value = userName,
                             singleLine = true,
                             shape = shapes.large,
                             modifier = Modifier.fillMaxWidth(),
                             colors = TextFieldDefaults.colors(
                                 focusedContainerColor = colorScheme.surface,
                                 unfocusedContainerColor = colorScheme.primary,
                                 disabledContainerColor = colorScheme.onSurface,
                             ),
                             onValueChange = onNameChange,
    //                         isError = {},
                             label = {
                                 Text(
                                     text = "ex:AvidmovieWatcher23",
                                     style = typography.bodySmall,
                                     fontWeight = FontWeight.Thin,
                                        color = colorScheme.onPrimary
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
             }
            val uiState by myViewModel.uiState.collectAsStateWithLifecycle()



        }
    }

}
@Preview
@Composable
fun LogInScreenPreview() {
    LogInScreen(
        topBarTitle = R.string.app_name,
        onNameChange = {},
        onKeyboardDone = {},
        userName = "username"
    )
}



//        // Check if the task is saved and call onTaskUpdate event
//        LaunchedEffect(uiState.isTaskSaved) {
//            if (uiState.isTaskSaved) {
//                onTaskUpdate()
//            }
//        }
