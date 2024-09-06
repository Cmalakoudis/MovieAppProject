package com.example.moviesapplicationcm

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.moviesapplicationcm.ui.MoviesApp

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