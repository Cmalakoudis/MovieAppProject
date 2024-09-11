package com.example.moviesapplicationcm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapplicationcm.data.AppUIState
import com.example.moviesapplicationcm.data.MovieItem
import com.example.moviesapplicationcm.data.MoviesRepository
import com.example.moviesapplicationcm.data.OfflineMoviesRepository
import com.example.moviesapplicationcm.data.UserPreferencesRepository
import com.example.moviesapplicationcm.model.Cast
import com.example.moviesapplicationcm.model.Movie
import com.example.moviesapplicationcm.model.MovieDbCastResponse
import com.example.moviesapplicationcm.model.MovieDbResponse
import com.example.moviesapplicationcm.model.MovieDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.checkerframework.checker.units.qual.m
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.roundToInt


class MovieViewModel(
    private val offlineMoviesRepository: OfflineMoviesRepository,
    private val moviesRepository: MoviesRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUIState())
    val uiState: StateFlow<AppUIState> = _uiState.asStateFlow()

    private lateinit var favouriteMovieList: List<Int>
    private lateinit var onlineData: MovieDbResponse
    private lateinit var movieDetails: MovieDetailsResponse
    private lateinit var movieCast: MovieDbCastResponse

    lateinit var navigateToLoginScreen: () -> Unit
    fun navigateToLogin(navigateLoginScreen: () -> Unit) {
        navigateToLoginScreen = navigateLoginScreen
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getMovieList()

        }

        viewModelScope.launch {
            collectPreferences()
        }
    }


    private suspend fun collectPreferences() {
        userPreferencesRepository.preferences.collect { userPreferences ->
            _uiState.update {
                it.copy(
                    movieAppUiState = _uiState.value.movieAppUiState.copy(
                        darkTheme = userPreferences.darkTheme,
                        isLoggedIn = userPreferences.isLoggedIn,
                        userName = userPreferences.userName
                    )
                )
            }
        }
    }

    private fun scanFavourites(){
        viewModelScope.launch(Dispatchers.IO) {
            offlineMoviesRepository.getMoviesList(uiState.value.movieAppUiState.userName).cancellable().collect { list ->
                if(list == null) {
                    favouriteMovieList = emptyList()
                } else {
                    favouriteMovieList = list
                }
                _uiState.update {
                    it.copy(
                        movieListData = _uiState.value.movieListData.copy(
                            movieList = _uiState.value.movieListData.movieList.map { movie ->
                                movie.copy(isFavourite = favouriteMovieList.contains(movie.id))
                            },
                            favouriteMovieIDs = favouriteMovieList
                        )
                    )
                }
                cancel()
            }
        }
    }

    //Load user profile after logging in
    fun loadUserProfile(navigateNext: () -> Unit) {
        scanFavourites()
        navigateNext()
        viewModelScope.launch {
            delay(1000)
            userPreferencesRepository.savePreferences(
                isDarkTheme = _uiState.value.movieAppUiState.darkTheme,
                isLoggedIn = true,
                username = _uiState.value.movieAppUiState.userName
            )
        }
    }

    //Make api call and gather online data
    fun getMovieList(page:Int = 0) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                onlineData = moviesRepository.getMoviesList(page)
                println("Successfully fetched data")
                collectMovies()
                _uiState.update {
                    it.copy(networkUiState = AppUIState.NetworkUiState.Success)
                }

            } catch (e: IOException) {
                _uiState.update {
                    it.copy(networkUiState = AppUIState.NetworkUiState.Error)
                }
            } catch (e: HttpException) {
                println("Something went wrong: $e")
                _uiState.update {
                    it.copy(networkUiState = AppUIState.NetworkUiState.Error)
                }
            }
        }
    }

    //Make api call and register extra movie details
    private suspend fun getMovieDetails() {
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


            } catch (e: IOException) {
                _uiState.update {
                    it.copy(networkUiState = AppUIState.NetworkUiState.Error)
                }
            } catch (e: HttpException) {
                println("Something went Wrong: $e")
                _uiState.update {
                    it.copy(networkUiState = AppUIState.NetworkUiState.Error)
                }
            }
        }.join()
        collectMovieDetails()

    }

    //Collect extra movie details and add to the movie list
    private fun collectMovieDetails() {

        val movie = _uiState.value.movieListData.movieList.find {
            it.id == _uiState.value.movieAppUiState.detailedMovieId
        }
        if (movie != null) {
            movie.genre = movieDetails.genres[0].name
            movie.runtime = movieDetails.runtime
            movie.cast = movieCast.cast.subList(0, movieCast.cast.size)
            movie.crew = movieCast.crew.find { it.department == "Directing" }!!
            movie.cast.forEach {
                it.profilePath = imageUrlPrefix.plus(it.profilePath)
            }
            movie.crew.profilePath = imageUrlPrefix.plus(movie.crew.profilePath)
        }

    }

    //Add movie data to the movie list
    private val imageUrlPrefix = "https://image.tmdb.org/t/p/original"
    private suspend fun collectMovies() {
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
                    movie.rating.toString().substring(0, 3),
                    movie.ratingVotes,
                    popularity = (movie.rating * 10).roundToInt()
                )
            )

            _uiState.update {
                it.copy(movieListData = it.movieListData.copy(movieList = movieLists),
                    movieAppUiState = _uiState.value.movieAppUiState.copy(
                        detailedMovieId = movie.id
                    ))
            }
            getMovieDetails()
        }
        scanFavourites()

    }

    //Display profile pop up
    fun onProfileClicked() {
        _uiState.update {
            it.copy(
                movieAppUiState = _uiState.value.movieAppUiState.copy(
                    profilePopUp = true,
                )
            )
        }
    }

    //Change theme
    fun changeTheme() {
        _uiState.update {
            it.copy(
                movieAppUiState = _uiState.value.movieAppUiState.copy(
                    darkTheme = !_uiState.value.movieAppUiState.darkTheme,
                )
            )
        }
        viewModelScope.launch {
            userPreferencesRepository.savePreferences(
                isDarkTheme = _uiState.value.movieAppUiState.darkTheme,
                isLoggedIn = _uiState.value.movieAppUiState.isLoggedIn,
                username = _uiState.value.movieAppUiState.userName
            )
        }
    }

    //Display details pop up
    fun onPressedCard(movie: Movie) {
        _uiState.update {
            it.copy(
                movieAppUiState = _uiState.value.movieAppUiState.copy(
                    detailedMovieId = movie.id,
                    detailsPopUp = true,
                )
            )
        }
    }

    //Close pop ups
    fun closePopUp() {
        _uiState.update {
            it.copy(
                movieAppUiState = _uiState.value.movieAppUiState.copy(
                    detailsPopUp = false,
                    profilePopUp = false,
                )
            )
        }
    }

    //ViewPopular or ViewFavourites
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
        loadFavourites()
        _uiState.update {
            it.copy(
                movieAppUiState = _uiState.value.movieAppUiState.copy(
                    viewingPopular = false,
                    sortingValue = "Default"
                )
            )
        }
        return false;
    }

    //Update user name on login
    fun updateUserName(name: String) {
        _uiState.update {
            it.copy(
                movieAppUiState = _uiState.value.movieAppUiState.copy(
                    userName = name,
                )
            )
        }
    }

    fun onSignOut() {
        _uiState.update {
            it.copy(
                movieAppUiState = _uiState.value.movieAppUiState.copy(
                    isLoggedIn = false,
                ),

            )
        }

        viewModelScope.launch {
            userPreferencesRepository.savePreferences(
                isDarkTheme = _uiState.value.movieAppUiState.darkTheme,
                isLoggedIn = false,
                username = _uiState.value.movieAppUiState.userName
            )
        }
        navigateToLoginScreen()
    }

    //Update user favourite preferences
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
        viewModelScope.launch(Dispatchers.IO) {
            offlineMoviesRepository.getMovieItem(_uiState.value.movieAppUiState.userName).cancellable().collect() {
                if (movie.isFavourite) {
                    offlineMoviesRepository.insertMovie(
                        MovieItem(
                            userName = _uiState.value.movieAppUiState.userName,
                            movieIds = movie.id
                        )
                    )

                } else {
                    offlineMoviesRepository.deleteMovie(movie.id)
                }
            cancel()
            }
        }
        return movie.isFavourite
    }

    fun getNextPage(){
        _uiState.update {
            it.copy(networkUiState = AppUIState.NetworkUiState.Loading)
        }
        if (_uiState.value.movieAppUiState.page < 494) {
            getMovieList(_uiState.value.movieAppUiState.page+3)
            _uiState.update {
                it.copy(movieAppUiState = it.movieAppUiState.copy(page = it.movieAppUiState.page + 3,
                    sortingValue = "Default"))
            }
        } else{
            _uiState.update {
                it.copy(networkUiState = AppUIState.NetworkUiState.Success)
            }
        }
    }

    fun getPreviousPage() {
        _uiState.update {
            it.copy(networkUiState = AppUIState.NetworkUiState.Loading)
        }
        if (_uiState.value.movieAppUiState.page > 2) {
            getMovieList(_uiState.value.movieAppUiState.page - 3)
            _uiState.update {
                it.copy(movieAppUiState = it.movieAppUiState.copy(page = it.movieAppUiState.page - 3,
                    sortingValue = "Default"))
            }
        } else {
            _uiState.update {
                it.copy(networkUiState = AppUIState.NetworkUiState.Success)
            }
        }
    }

    fun expandSorting(){
        _uiState.update {
            it.copy(
                movieAppUiState = _uiState.value.movieAppUiState.copy(
                    expandedSorting = !_uiState.value.movieAppUiState.expandedSorting
                )
            )
        }
    }

    fun sortMovies(sortType: String) {
        when (sortType) {
            "Runtime" -> {
                _uiState.update {
                    it.copy(
                        movieListData = it.movieListData.copy(
                            movieList = it.movieListData.movieList.sortedByDescending { it.runtime },
                            favouriteMovieDisplay = it.movieListData.favouriteMovieDisplay.sortedByDescending { it.runtime }
                        ),
                        movieAppUiState = it.movieAppUiState.copy(
                            sortingValue = sortType
                        )
                    )
                }
            }
            "Rating" -> {
                _uiState.update {
                    it.copy(
                        movieListData = it.movieListData.copy(
                            movieList = it.movieListData.movieList.sortedByDescending { it.rating },
                            favouriteMovieDisplay = it.movieListData.favouriteMovieDisplay.sortedByDescending { it.rating }
                        ),
                        movieAppUiState = it.movieAppUiState.copy(
                            sortingValue = sortType
                        )
                    )
                }
            }
            "Release Date" -> {
                _uiState.update {
                    it.copy(
                        movieListData = it.movieListData.copy(
                            movieList = it.movieListData.movieList.sortedByDescending { it.releaseDate },
                            favouriteMovieDisplay = it.movieListData.favouriteMovieDisplay.sortedByDescending { it.releaseDate }
                        ),
                        movieAppUiState = it.movieAppUiState.copy(
                            sortingValue = sortType
                        )
                    )
                }
            }
            "Review count" -> {
                _uiState.update {
                    it.copy(
                        movieListData = it.movieListData.copy(
                            movieList = it.movieListData.movieList.sortedByDescending { it.ratingVotes },
                            favouriteMovieDisplay = it.movieListData.favouriteMovieDisplay.sortedByDescending { it.ratingVotes }
                        ),
                        movieAppUiState = it.movieAppUiState.copy(
                            sortingValue = sortType
                        )
                    )
                }
            }
            "Default" -> {
                _uiState.update {
                    it.copy(
                        movieListData = it.movieListData.copy(
                            movieList = it.movieListData.movieList.sortedByDescending { it.popularity },
                            favouriteMovieDisplay = it.movieListData.favouriteMovieDisplay.sortedByDescending { it.popularity }
                        ),
                        movieAppUiState = it.movieAppUiState.copy(
                            sortingValue = sortType
                        )
                    )
                }
            }
        }
    }

    private fun loadFavourites(){
        var movieList: MutableList<Movie> = mutableListOf()
        _uiState.value.movieListData.favouriteMovieIDs.forEach { movieId ->
            var movie: Movie? = _uiState.value.movieListData.movieList.find { it.id == movieId}
            if (movie != null) {
                movieList.add(movie)
            }
        }
        _uiState.update {
            it.copy(
                movieListData = it.movieListData.copy(
                    favouriteMovieDisplay = movieList
                ))
        }
    }

    fun updateSearchQuery(query:String) {

        _uiState.update {
            it.copy(
                movieAppUiState = _uiState.value.movieAppUiState.copy(
                    searchQuery = query,
                ),
            )
        }
    }

    fun searchMovie(){
        _uiState.update {
            it.copy(networkUiState = AppUIState.NetworkUiState.Loading)
        }
        val query = _uiState.value.movieAppUiState.searchQuery
        var movieListTitle: List<Movie> =
            _uiState.value.movieListData.movieList.filter { it.title.contains(query, ignoreCase = true)}

        var movieListActor: List<Movie> = _uiState.value.movieListData.movieList.filter { if(it.cast.find { it.name.equals(query, ignoreCase = true) } != null) true else false }

        var movieListDirector: List<Movie> =
            _uiState.value.movieListData.movieList.filter { it.crew.name.contains(query, ignoreCase = true)}

        _uiState.update {
            it.copy(
                networkUiState = AppUIState.NetworkUiState.Success,
                movieListData = it.movieListData.copy(
                    queryMovieDisplay = movieListTitle + movieListActor + movieListDirector
                ),
                movieAppUiState = _uiState.value.movieAppUiState.copy(
                    viewingPopular = false,
                    viewingSearched = true,
                ),
            )
        }
    }

    fun stopSearch() {
        _uiState.update {
            it.copy(
                movieAppUiState = _uiState.value.movieAppUiState.copy(
                    viewingSearched = false,
                    viewingPopular = true,
                    searchQuery = "",
                ),
            )
        }
    }
}
