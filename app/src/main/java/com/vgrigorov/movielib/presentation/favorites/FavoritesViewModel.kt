package com.vgrigorov.movielib.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vgrigorov.movielib.data.favourites.FavoritesRepository
import com.vgrigorov.movielib.domain.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    val favoriteMovies: Flow<List<Movie>> =  favoritesRepository.getAllMovies()

    fun removeMovieFromFavorites(movieId: Int) {
        viewModelScope.launch {
            favoritesRepository.removeMovie(movieId)
        }
    }
}