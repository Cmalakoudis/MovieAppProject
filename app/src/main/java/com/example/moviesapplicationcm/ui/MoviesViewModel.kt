package com.example.moviesapplicationcm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapplicationcm.data.AppUIState
import com.example.moviesapplicationcm.data.MoviesRepository
import com.example.moviesapplicationcm.data.OfflineMoviesRepository
import com.example.moviesapplicationcm.model.Movie
import com.example.moviesapplicationcm.model.MovieDbCastResponse
import com.example.moviesapplicationcm.model.MovieDbResponse
import com.example.moviesapplicationcm.model.MovieDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.roundToInt


class MovieViewModel(
    private val offlineMoviesRepository: OfflineMoviesRepository,
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUIState())
    val uiState: StateFlow<AppUIState> = _uiState.asStateFlow()

    private lateinit var offlineData: Flow<List<String>>
    private lateinit var onlineData: MovieDbResponse
    private lateinit var movieDetails: MovieDetailsResponse
    private lateinit var movieCast: MovieDbCastResponse

    init {
        viewModelScope.launch(Dispatchers.IO) {
//            offlineMoviesRepository.insertMovie(MovieItem(title = "despicables"))
            getMovieList()
//            offlineData = offlineMoviesRepository.getMoviesList()
        }
    }

    fun getMovieList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                onlineData = moviesRepository.getMoviesList()
                println("GOT THE GOOOOOOOOOOOOOOOOOOOODs")
                _uiState.update {
                    it.copy(networkUiState = AppUIState.NetworkUiState.Success)
                }
                collectMovies()

            } catch (e: IOException) {
                _uiState.update {
                    it.copy(networkUiState = AppUIState.NetworkUiState.Error)
                }
            } catch (e: HttpException) {
                println("I FUCKED UUUUUUUUUUUUUUUUUUUUUUUUUP" + e)
                _uiState.update {
                    it.copy(networkUiState = AppUIState.NetworkUiState.Error)
                }
            }
        }
    }

    fun getMovieDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                movieDetails = _uiState.value.movieAppUiState.detailedMovieId?.let {
                    moviesRepository.getMovieDetails(
                        it
                    )
                }!!
                movieCast = _uiState.value.movieAppUiState.detailedMovieId?.let {
                    moviesRepository.getCastDetails(
                        it
                    )
                }!!

                collectMovieDetails()

            } catch (e: IOException) {
                _uiState.update {
                    it.copy(networkUiState = AppUIState.NetworkUiState.Error)
                }
            } catch (e: HttpException) {
                println("I FUCKED UUUUUUUUUUUUUUUUUUUUUUUUUP" + e)
                _uiState.update {
                    it.copy(networkUiState = AppUIState.NetworkUiState.Error)
                }
            }
        }

    }

    private fun collectMovieDetails() {

        val movie = _uiState.value.movieListData.movieList.find {
            it.id == _uiState.value.movieAppUiState.detailedMovieId
        }
        if (movie != null) {
            movie.genre = movieDetails.genres[0].name
            movie.runtime = movieDetails.runtime
            movie.cast = movieCast.cast.subList(0,6)
            movie.crew = movieCast.crew.find { it.department == "Directing" }!!
            if(movie.crew == null) {
                return
            }
            movie.cast.forEach {
                it.profilePath = imageUrlPrefix.plus(it.profilePath)
            }
            movie.crew.profilePath = imageUrlPrefix.plus(movie.crew.profilePath)
        }

    }

    private val imageUrlPrefix = "https://image.tmdb.org/t/p/original"
    private fun collectMovies() {
        val movieLists: MutableList<Movie> = mutableListOf()
        onlineData.movies.forEach() { movie ->
            movieLists.add(
                Movie(
                    movie.id,
                    movie.title,
                    movie.overview,
                    imageUrlPrefix.plus(movie.posterPath),
                    imageUrlPrefix.plus(movie.backRoundPath),
                    movie.releaseDate,
                    movie.rating.toString().substring(0,3),
                    movie.ratingVotes,
                    popularity = (movie.rating*10).roundToInt()
                )
            )

            _uiState.update {
                it.copy(movieListData = AppUIState.MovieListData(movieList = movieLists))
            }
        }


    }

    fun changeTheme() {
        _uiState.update {
            it.copy(
                movieAppUiState = _uiState.value.movieAppUiState.copy(
                    darkTheme = !_uiState.value.movieAppUiState.darkTheme,
                )
            )
        }
    }

    fun onPressedCard(movie: Movie) {
        _uiState.update {
            it.copy(
                movieAppUiState = _uiState.value.movieAppUiState.copy(
                    detailedMovieId = movie.id,
                    detailsPopUp = true,
                )
            )
        }
        getMovieDetails()
    }

    fun closePopUp() {
        _uiState.update {
            it.copy(
                movieAppUiState = _uiState.value.movieAppUiState.copy(
                    detailsPopUp = false,
                )
            )
        }
    }

    fun navigateToDetailsScreen() {
        _uiState.update {
            it.copy(
                movieAppUiState = _uiState.value.movieAppUiState.copy(
                    detailsPopUp = false,
                )
            )
        }
    }

    fun isDarkTheme(): Boolean {
        return _uiState.value.movieAppUiState.darkTheme
    }

    fun viewingPopular(): Boolean {
        _uiState.update {
            it.copy(
                movieAppUiState = _uiState.value.movieAppUiState.copy(
                    viewingPopular = true,
                )
            )
        }
        return true;
    }

    fun viewingFavourites(): Boolean {
        _uiState.update {
            it.copy(
                movieAppUiState = _uiState.value.movieAppUiState.copy(
                    viewingPopular = false,
                )
            )
        }
        return false;
    }

    fun updateUserName(name: String) {
        _uiState.update {
            it.copy(
                movieAppUiState = _uiState.value.movieAppUiState.copy(
                    userName = name,
                )
            )
        }
    }

    fun makeFavourite(movie: Movie): Boolean {
        movie.isFavourite = !movie.isFavourite
        val newIDs: List<Int> = if (movie.isFavourite) {
            _uiState.value.movieListData.favouriteMovieIDs.plus(movie.id)
        } else {
            _uiState.value.movieListData.favouriteMovieIDs.minus(movie.id)
        }
        _uiState.value.movieListData.movieList.forEach() {
            if (it.id == movie.id) {
                it.isFavourite = movie.isFavourite
            }
        }
        _uiState.update {
            it.copy(
                movieListData = _uiState.value.movieListData.copy(
                    favouriteMovieIDs = newIDs
                )
            )
        }
        return movie.isFavourite
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
