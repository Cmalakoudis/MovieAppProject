package com.example.moviesapplicationcm

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
            MoviesApplicationCMTheme {
//                    LogInScreen(        topBarTitle = R.string.app_name,
//                        onNameChange = {},
//                        onKeyboardDone = {},
//                        userName = "")
                    PreviewLoginInfoPopUp()
                    }
        }
    }
}
