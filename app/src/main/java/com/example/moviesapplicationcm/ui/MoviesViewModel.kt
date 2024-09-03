package com.example.moviesapplicationcm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapplicationcm.data.AppUIState
import com.example.moviesapplicationcm.data.MovieItem
import com.example.moviesapplicationcm.data.MoviesRepository
import com.example.moviesapplicationcm.data.OfflineMoviesRepository
import com.example.moviesapplicationcm.data.UserPreferencesRepository
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
    private val moviesRepository: MoviesRepository,
    private val userPreferencesRepository: UserPreferencesRepository
    ) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUIState())
    val uiState: StateFlow<AppUIState> = _uiState.asStateFlow()

    private lateinit var favouriteMovieList: Flow<List<Int>>
    private lateinit var appPreferences: Flow<AppUIState.MovieAppUiState>
    private lateinit var onlineData: MovieDbResponse
    private lateinit var movieDetails: MovieDetailsResponse
    private lateinit var movieCast: MovieDbCastResponse


    init {
        viewModelScope.launch(Dispatchers.IO) {
            getPreferences()
            getMovieList()

        }
    }

    //Get User preferences
     private suspend fun getPreferences() {
        lateinit var mypreferences: AppUIState.MovieAppUiState
        appPreferences = userPreferencesRepository.preferences
        val init = viewModelScope.launch(Dispatchers.IO) {
             appPreferences.collect { userPreferences ->
                 mypreferences = AppUIState.MovieAppUiState(darkTheme = userPreferences.darkTheme,
                     isLoggedIn = userPreferences.isLoggedIn, userName = userPreferences.userName)
            }
        }
        init.join()
        _uiState.update {
            it.copy(
                movieAppUiState = _uiState.value.movieAppUiState.copy(
                    darkTheme = mypreferences.darkTheme,
                    isLoggedIn = mypreferences.isLoggedIn,
                    userName = mypreferences.userName
                )
            )
        }
    }


//    fun selectLayout(isLinearLayout: Boolean) {
//        viewModelScope.launch {
//            userPreferencesRepository.saveLayoutPreference(isLinearLayout)
//        }
//    }
//    super {
//        viewModelScope.launch(Dispatchers.IO) {
//            offlineMoviesRepos`itory.deleteMovie(_uiState.value.movieAppUiState.detailedMovieId!!)
//        }
//    }
    //Load user profile after logging in
    fun loadUserProfile(navigateNext:()->Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            favouriteMovieList = offlineMoviesRepository.getMoviesList(uiState.value.movieAppUiState.userName)
        }
        navigateNext()
    }

    //Make api call and gather online data
    fun getMovieList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                onlineData = moviesRepository.getMoviesList()
                println("Successfully fetched data")
                _uiState.update {
                    it.copy(networkUiState = AppUIState.NetworkUiState.Success)
                }
                collectMovies()

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
    private fun getMovieDetails() {
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
                println("Something went Wrong: $e")
                _uiState.update {
                    it.copy(networkUiState = AppUIState.NetworkUiState.Error)
                }
            }
        }

    }

    //Collect extra movie details and add to the movie list
    private fun collectMovieDetails() {

        val movie = _uiState.value.movieListData.movieList.find {
            it.id == _uiState.value.movieAppUiState.detailedMovieId
        }
        if (movie != null) {
            movie.genre = movieDetails.genres[0].name
            movie.runtime = movieDetails.runtime
            movie.cast = movieCast.cast.subList(0,6)
            movie.crew = movieCast.crew.find { it.department == "Directing" }!!
            movie.cast.forEach {
                it.profilePath = imageUrlPrefix.plus(it.profilePath)
            }
            movie.crew.profilePath = imageUrlPrefix.plus(movie.crew.profilePath)
        }

    }

    //Add movie data to the movie list
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
        getMovieDetails()
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
        _uiState.update {
            it.copy(
                movieAppUiState = _uiState.value.movieAppUiState.copy(
                    viewingPopular = false,
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
            offlineMoviesRepository.getMovieItem(_uiState.value.movieAppUiState.userName).collect {
                    if (movie.isFavourite) {
                        offlineMoviesRepository.updateMovie(
                            MovieItem(
                                id = it.id,
                                userName = it.userName,
                                movieIds = it.movieIds.plus(movie.id)
                            )
                        )
                    }else {
                            offlineMoviesRepository.updateMovie(MovieItem(id = it.id, userName = it.userName, movieIds = it.movieIds.minus(movie.id)))
                    }
            }
        }
        return movie.isFavourite
    }

}
