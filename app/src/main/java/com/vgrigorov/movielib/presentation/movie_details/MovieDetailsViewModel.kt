package com.vgrigorov.movielib.presentation.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vgrigorov.movielib.data.favourites.FavoritesRepository
import com.vgrigorov.movielib.domain.GetThrailerUrlUseCase
import com.vgrigorov.movielib.domain.base.DataResult
import com.vgrigorov.movielib.domain.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val getThrailerUrlUseCase: GetThrailerUrlUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun checkMovieData(movie: Movie?) {
        viewModelScope.launch(Dispatchers.IO) {
            //fake loading since we already have the data
            delay(1000L)

            if (movie != null) {
                val isFav = favoritesRepository.isMovieFavorite(movie.id)
                val url = when (val urlResult = getThrailerUrlUseCase.execute(movie.id)) {
                    is DataResult.Failure -> ""
                    is DataResult.Success -> {
                        urlResult.value
                    }
                }
                _uiState.value =
                    UiState.Success(movie, isFav , url )
            } else {
                _uiState.value = UiState.Error("Movie details not found!")
            }
        }
    }

    fun addMovieToFavourites(movie: Movie) {
        viewModelScope.launch {
            favoritesRepository.addMovie(movie)
        }
    }

}

sealed class UiState {
    object Loading : UiState()
    data class Success(val movie: Movie, val isMovieFavorite: Boolean , val thrailerUrl :String) : UiState()
    data class Error(val message: String) : UiState()
}