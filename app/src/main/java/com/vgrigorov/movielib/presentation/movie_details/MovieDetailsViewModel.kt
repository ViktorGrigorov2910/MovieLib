package com.vgrigorov.movielib.presentation.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vgrigorov.movielib.domain.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun checkMovieData(movie: Movie?) {
        viewModelScope.launch {
            //fake loading since we already have the data
            delay(2000L)

            if (movie != null) {
                _uiState.value = UiState.Success(movie)
            } else {
                _uiState.value = UiState.Error("Movie details not found!")
            }
        }
    }

}

sealed class UiState {
    object Loading : UiState()
    data class Success(val movie: Movie) : UiState()
    data class Error(val message: String) : UiState()
}