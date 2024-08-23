package com.example.moviesapplicationcm.ui

import com.example.moviesapplicationcm.R
import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moviesapplicationcm.ui.MovieViewModel
import com.example.moviesapplicationcm.ui.screens.MoviesTopAppBar

/**
 * enum values that represent the screens in the app
 */
enum class MovieAppScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Movies(title = R.string.Movies),
    MovieDetails(title = R.string.Movie_Details),
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieAppBar(
    currentScreen: MovieAppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_arrow)
                    )
                }
            }
        }
    )
}
@Composable
fun MoviesApp(
    viewModel: MovieViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = MovieAppScreen.valueOf(
        backStackEntry?.destination?.route ?: MovieAppScreen.Start.name
    )

    Scaffold(
        topBar = {
            MoviesTopAppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = MovieAppScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(route = MovieAppScreen.Start.name) {
//                StartOrderScreen(
//                    quantityOptions = DataSource.quantityOptions,
//                    onNextButtonClicked = {
//                        viewModel.setQuantity(it)
//                        navController.navigate(MovieAppScreen.Flavor.name)
//                    },
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(dimensionResource(R.dimen.padding_medium))
//                )
            }
            composable(route = MovieAppScreen.Movies.name) {
//                val context = LocalContext.current
//                SelectOptionScreen(
//                    subtotal = uiState.price,
//                    onNextButtonClicked = { navController.navigate(MovieAppScreen.Pickup.name) },
//                    onCancelButtonClicked = {
//                        cancelOrderAndNavigateToStart(viewModel, navController)
//                    },
//                    options = DataSource.flavors.map { id -> context.resources.getString(id) },
//                    onSelectionChanged = { viewModel.setFlavor(it) },
//                    modifier = Modifier.fillMaxHeight()
//                )
            }
            composable(route = MovieAppScreen.MovieDetails.name) {
//                SelectOptionScreen(
//                    subtotal = uiState.price,
//                    onNextButtonClicked = { navController.navigate(MovieAppScreen.Summary.name) },
//                    onCancelButtonClicked = {
//                        cancelOrderAndNavigateToStart(viewModel, navController)
//                    },
//                    options = uiState.pickupOptions,
//                    onSelectionChanged = { viewModel.setDate(it) },
//                    modifier = Modifier.fillMaxHeight()
//                )
            }
        }
    }
}

/**
 * Resets the [OrderUiState] and pops up to [MovieAppScreen.Start]
 */
private fun cancelOrderAndNavigateToStart(
    viewModel: MovieViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(MovieAppScreen.Start.name, inclusive = false)
}

/**
 * Creates an intent to share order details
 */
private fun shareMovie(context: Context, subject: String, summary: String) {
    // Create an ACTION_SEND implicit intent with order details in the intent extras
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.app_name)
        )
    )
}
