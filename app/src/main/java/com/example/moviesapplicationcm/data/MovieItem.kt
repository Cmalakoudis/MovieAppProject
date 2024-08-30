package com.example.moviesapplicationcm.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesapplicationcm.model.Movie
import org.checkerframework.checker.nullness.qual.NonNull

@Entity(tableName = "FavouriteMovies")
data class MovieItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String
)