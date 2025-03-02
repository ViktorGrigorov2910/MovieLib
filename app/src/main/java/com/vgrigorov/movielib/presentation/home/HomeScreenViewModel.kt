package com.vgrigorov.movielib.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vgrigorov.movielib.domain.GetPopularMoviesUseCase
import com.vgrigorov.movielib.domain.base.DataResult
import com.vgrigorov.movielib.domain.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    // State for top-rated movies
    private val _topRatedMovies = MutableStateFlow<List<Movie>>(emptyList())
    val topRatedMovies: StateFlow<List<Movie>> = _topRatedMovies

    // State for popular movies
    private val _popularMovies = MutableStateFlow<List<Movie>>(emptyList())
    val popularMovies: StateFlow<List<Movie>> = _popularMovies

    // State for now-playing movies
    private val _nowPlayingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val nowPlayingMovies: StateFlow<List<Movie>> = _nowPlayingMovies

    // State for loading/error
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            delay(2000L)

//            when (val result = getPopularMoviesUseCase.execute(Unit)) {
//                is DataResult.Failure -> _uiState.value =
//                    UiState.Error(result.error.message ?: "Unknown error")
//
//                is DataResult.Success -> {

                    //TODO: Uncomment when testing is finished
                    _popularMovies.value = dummyMovies
                    _nowPlayingMovies.value = dummyMovies
                    _topRatedMovies.value = dummyMovies
//                    _popularMovies.value = result.value.movies
//                    _nowPlayingMovies.value = result.value.movies
//                    _topRatedMovies.value = result.value.movies
//                }
//            }

            //TODO: Implement when getTopRatedMovies & getNowPlayingMovies api is ready
            //when (val result =getTopRatedMoviesUseCase.execute(Unit) ){
            //                is DataResult.Failure -> _uiState.value = UiState.Error(result.error.message ?: "Unknown error")
            //                is DataResult.Success -> _topRatedMovies.value = result.value.movies
            //            }

            //when (val result =getNowPlayingMoviesUseCase.execute(Unit) ){
            //                is DataResult.Failure -> _uiState.value = UiState.Error(result.error.message ?: "Unknown error")
            //                is DataResult.Success -> _nowPlayingMovies.value = result.value.movies
            //            }
            //
            _uiState.value = UiState.Success
        }
    }
}


val dummyMovies = listOf(
    Movie(
        id = 1,
        title = "Inception",
        posterPath = "https://image.tmdb.org/t/p/w500/qmDpIHrmpJINaRKAfWQfftjCdyi.jpg",
        backdropPath = "https://image.tmdb.org/t/p/w1280/s3TBrRGB1iav7gFOCNx3H31MoES.jpg",
        releaseDate = "2010-07-16",
        rating = 8.8,
        overview = "A thief who enters the dreams of others to steal secrets from their subconscious."
    ),
    Movie(
        id = 2,
        title = "The Dark Knight",
        posterPath = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
        backdropPath = "https://image.tmdb.org/t/p/w1280/hjQp148VjWF4KjzhsD90OCMr11h.jpg",
        releaseDate = "2008-07-18",
        rating = 9.0,
        overview = "Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham."
    ),
    Movie(
        id = 3,
        title = "Interstellar",
        posterPath = "https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg",
        backdropPath = "https://image.tmdb.org/t/p/w1280/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg",
        releaseDate = "2014-11-07",
        rating = 8.6,
        overview = "Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.A group of explorers travel through a wormhole in space to ensure humanity's survival."
    ),
    Movie(
        id = 4,
        title = "Avatar",
        posterPath = "https://image.tmdb.org/t/p/w500/kyeqWdyUXW608qlYkRqosgbbJyK.jpg",
        backdropPath = "https://image.tmdb.org/t/p/w1280/oM8s40cTb4Z9U1NzqUe6wXBpyUb.jpg",
        releaseDate = "2009-12-18",
        rating = 7.9,
        overview = "Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.A paraplegic Marine is dispatched to the moon Pandora and becomes part of an indigenous race."
    ),
    Movie(
        id = 5,
        title = "Parasite",
        posterPath = "https://image.tmdb.org/t/p/w500/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg",
        backdropPath = "https://image.tmdb.org/t/p/w1280/TgH9POOinptF9zRzj4uJROK1oNj.jpg",
        releaseDate = "2019-05-30",
        rating = 8.5,
        overview = "Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.Greed and class discrimination threaten the newly formed symbiotic relationship between a wealthy family and a poor one."
    )
)


sealed class UiState {
    object Loading : UiState()
    object Success : UiState()
    data class Error(val message: String) : UiState()
}