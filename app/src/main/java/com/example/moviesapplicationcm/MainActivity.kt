package com.example.moviesapplicationcm

import android.os.Bundle
import android.view.Surface
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviesapplicationcm.ui.MovieViewModel
import com.example.moviesapplicationcm.ui.MoviesApp
import com.example.moviesapplicationcm.ui.MoviesViewModelProvider
import com.example.moviesapplicationcm.ui.screens.MovieDetailsPreview
import com.example.moviesapplicationcm.ui.screens.MovieDetailsScreen
import com.example.moviesapplicationcm.ui.screens.MovieListScreen
import com.example.moviesapplicationcm.ui.screens.PreviewLoginInfoPopUp
import com.example.moviesapplicationcm.ui.screens.PreviewMovieListScreen
import com.example.moviesapplicationcm.ui.theme.MoviesApplicationCMTheme

class MainActivity : ComponentActivity() {
    lateinit var myImageButton: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

                MoviesApp()

        }
    }
}