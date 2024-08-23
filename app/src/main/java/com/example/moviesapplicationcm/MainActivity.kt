package com.example.moviesapplicationcm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.moviesapplicationcm.ui.screens.LogInScreen
import com.example.moviesapplicationcm.ui.theme.MoviesApplicationCMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesApplicationCMTheme {
                LogInScreen(        topBarTitle = R.string.app_name,
                    onNameChange = {},
                    onKeyboardDone = {},
                    userName = "")
            }
        }
    }
}
