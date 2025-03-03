package com.vgrigorov.movielib.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vgrigorov.movielib.domain.GetPopularMoviesUseCase
import com.vgrigorov.movielib.domain.GetTopRatedMoviesUseCase
import com.vgrigorov.movielib.domain.GetNowPlayingMoviesUseCase
import com.vgrigorov.movielib.domain.base.DataResult
import com.vgrigorov.movielib.domain.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
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

            when (val result = getPopularMoviesUseCase.execute(Unit)) {
                is DataResult.Failure -> _uiState.value =
                    UiState.Error(result.error.message ?: "Unknown error")

                is DataResult.Success -> {
                    _popularMovies.value = result.value.movies
                }
            }

            //TODO: Implement when getTopRatedMovies & getNowPlayingMovies api is ready
            when (val result = getTopRatedMoviesUseCase.execute(Unit)) {
                is DataResult.Failure -> _uiState.value =
                    UiState.Error(result.error.message ?: "Unknown error")

                is DataResult.Success -> _topRatedMovies.value = result.value.movies
            }

            when (val result = getNowPlayingMoviesUseCase.execute(Unit)) {
                is DataResult.Failure -> _uiState.value =
                    UiState.Error(result.error.message ?: "Unknown error")

                is DataResult.Success -> _nowPlayingMovies.value = result.value.movies
            }

            _uiState.value = UiState.Success
        }
    }
}


sealed class UiState {
    object Loading : UiState()
    object Success : UiState()
    data class Error(val message: String) : UiState()
}