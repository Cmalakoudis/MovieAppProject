package com.example.moviesapplicationcm.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material3.ColorScheme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.moviesapplicationcm.R
import com.example.moviesapplicationcm.data.AppUIState
import com.example.moviesapplicationcm.data.MoviesRepository
import com.example.moviesapplicationcm.data.OfflineMoviesRepository
import com.example.moviesapplicationcm.model.Movie
import com.example.moviesapplicationcm.model.MovieDbResponse
import com.example.moviesapplicationcm.ui.theme.MoviesApplicationCMTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MovieViewModel(private val offlineMoviesRepository: OfflineMoviesRepository, private val moviesRepository: MoviesRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUIState())
    val uiState: StateFlow<AppUIState> = _uiState.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = AppUIState()
    )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private lateinit var offlineData: Flow<List<String>>
    private lateinit var onlineData: MovieDbResponse
    init {
        viewModelScope.launch(Dispatchers.IO) {
//            offlineMoviesRepository.insertMovie(MovieItem(title = "despicables"))
            try {
                onlineData = moviesRepository.getMoviesList()
                println("GOT THE GOOOOOOOOOOOOOOOOOOOODs")

            } catch (e: Exception){

                println("I FUCKED UUUUUUUUUUUUUUUUUUUUUUUUUP"+ e)
            }
//            offlineData = offlineMoviesRepository.getMoviesList()
        }
    }


    fun loadMovies(){
        viewModelScope.launch {

        }
    }

    val imageUrlPrefix = "https://image.tmdb.org/t/p/original"
    suspend fun collect(){
        val movieLists :MutableList<Movie> = mutableListOf()
        onlineData.movies.forEach() { movie ->
                movieLists.add(Movie(movie.id, movie.title, movie.overview, imageUrlPrefix.plus(movie.posterPath),
                    imageUrlPrefix.plus(movie.backRoundPath), movie.releaseDate, movie.rating.toString(), movie.ratingVotes))

                _uiState.update {
                    it.copy(movieListData = AppUIState.MovieListData(movieList = movieLists))
                }
            }


    }

    fun tryToPrint (){
        viewModelScope.launch(Dispatchers.IO) { collect()  }
    }
    fun changeTheme() {
        _uiState.update {
            it.copy(
                movieAppUiState = AppUIState.MovieAppUiState(
                    darkTheme = !_uiState.value.movieAppUiState.darkTheme,
                )
            )
        }
    }

    fun onPressedCard(movie: Movie) {
        _uiState.update {
            it.copy(
                movieAppUiState = AppUIState.MovieAppUiState(
                    detailedMovie = movie,
                    detailsPopUp = true,
                )
            )
        }
    }
    fun closePopUp() {
        _uiState.update {
            it.copy(
                movieAppUiState = AppUIState.MovieAppUiState(
                    detailsPopUp = false,
                )
            )
        }
    }
    fun navigateToDetailsScreen() {
        _uiState.update {
            it.copy(
                movieAppUiState = AppUIState.MovieAppUiState(
                    detailsPopUp = false,
                )
            )
        }
    }
//    private val _movies = MutableStateFlow<PagingData>(MovieUiState())
//    val movies: StateFlow<MovieUiState> = _movies.asStateFlow()
//    private fun getMoviesList() {
//        viewModelScope.launch {
//            try {
//    val listResult = moviesRepository.getMoviesList()
//
//            }
//        }
//    }


//    fun setQuantity(numberCupcakes: Int) {
//        _uiState.update { currentState ->
//            currentState.copy(
//                quantity = numberCupcakes,
//                price = calculatePrice(quantity = numberCupcakes)
//            )
//        }
//    }
//
//    /**
//     * Set the [desiredFlavor] of cupcakes for this order's state.
//     * Only 1 flavor can be selected for the whole order.
//     */
//    fun setFlavor(desiredFlavor: String) {
//        _uiState.update { currentState ->
//            currentState.copy(flavor = desiredFlavor)
//        }
//    }
//
//    /**
//     * Set the [pickupDate] for this order's state and update the price
//     */
//    fun setDate(pickupDate: String) {
//        _uiState.update { currentState ->
//            currentState.copy(
//                date = pickupDate,
//                price = calculatePrice(pickupDate = pickupDate)
//            )
//        }
//    }
//
//    /**
//     * Reset the order state
//     */
//    fun resetOrder() {
//        _uiState.value = MovieUiState()
//    }
//
//    /**
//     * Returns the calculated price based on the order details.
//     */
//    private fun calculatePrice(
//        quantity: Int = _uiState.value.quantity,
//        pickupDate: String = _uiState.value.date
//    ): String {
//        var calculatedPrice = quantity * PRICE_PER_CUPCAKE
//        // If the user selected the first option (today) for pickup, add the surcharge
//        if (pickupOptions()[0] == pickupDate) {
//            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
//        }
//        val formattedPrice = NumberFormat.getCurrencyInstance().format(calculatedPrice)
//        return formattedPrice
//    }
//
//    /**
//     * Returns a list of date options starting with the current date and the following 3 dates.
//     */
//    private fun pickupOptions(): List<String> {
//        val dateOptions = mutableListOf<String>()
//        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
//        val calendar = Calendar.getInstance()
//        // add current date and the following 3 dates.
//        repeat(4) {
//            dateOptions.add(formatter.format(calendar.time))
//            calendar.add(Calendar.DATE, 1)
//        }
//        return dateOptions
//    }
}
