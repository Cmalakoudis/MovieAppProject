package com.example.moviesapplicationcm.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.moviesapplicationcm.model.Movie
import kotlinx.coroutines.flow.Flow

    /**
     * Provides access to read/write operations on the schedule table.
     * Used by the view models to format the query results for use in the UI.
     */
    @Dao
    interface MoviesDao {

        @Query("DELETE FROM favouritemovies")
        suspend fun deleteAll()

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertMovie(movie: MovieItem)

        @Delete
        suspend fun deleteMovie(movie: MovieItem)

        @Update
        suspend fun updateMovie(movie: MovieItem)

        @Query("DELETE FROM favouritemovies WHERE movieIds = :id")
        suspend fun deleteMovie(id: Int)

        @Query("SELECT movieIds from favouritemovies Where userName == :userName")
         fun getMoviesList(userName:String): Flow<List<Int>>

         @Query("SELECT * from favouritemovies Where userName == :userName")
         fun getMovieItem(userName: String): Flow<MovieItem>

//        @Query(
//            """
//        SELECT * FROM schedule
//        WHERE stop_name = :stopName
//        ORDER BY arrival_time ASC
//        """
//        )
    }