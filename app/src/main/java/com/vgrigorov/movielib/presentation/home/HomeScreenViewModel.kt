package com.vgrigorov.movielib.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.vgrigorov.movielib.domain.GetPopularMoviesUseCase
import com.vgrigorov.movielib.domain.GetTopRatedMoviesUseCase
import com.vgrigorov.movielib.domain.GetNowPlayingMoviesUseCase
import com.vgrigorov.movielib.domain.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
) : ViewModel() {

    // State for top-rated movies
    private val _topRatedMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val topRatedMovies: State<Flow<PagingData<Movie>>> = _topRatedMovies

    // State for popular movies
    private val _popularMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val popularMovies:State<Flow<PagingData<Movie>>> = _popularMovies

    // State for now-playing movies
    private val _nowPlayingMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val nowPlayingMovies:State<Flow<PagingData<Movie>>> = _nowPlayingMovies

    // State for loading/error
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            _popularMovies.value = getPopularMoviesUseCase.execute(Unit).map { result ->
                result.filter { ((it.posterPath != null)) }
            }.cachedIn(viewModelScope)


            _topRatedMovies.value = getTopRatedMoviesUseCase.execute(Unit).map { result ->
                result.filter { ((it.posterPath != null)) }
            }.cachedIn(viewModelScope)

            _nowPlayingMovies.value = getNowPlayingMoviesUseCase.execute(Unit).map { result ->
                result.filter { ((it.posterPath != null)) }
            }.cachedIn(viewModelScope)


            _uiState.value = UiState.Success
        }
    }
}

sealed class UiState {
    object Loading : UiState()
    object Success : UiState()
    data class Error(val message: String) : UiState()
}