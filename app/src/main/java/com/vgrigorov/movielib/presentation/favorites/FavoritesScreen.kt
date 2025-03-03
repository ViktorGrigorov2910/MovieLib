package com.vgrigorov.movielib.presentation.favorites

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.vgrigorov.movielib.presentation.home.MovieComponent

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favoriteMovies by viewModel.favoriteMovies.collectAsState(initial = emptyList())

    //TODO: Make the favorites a Row
    // Add swipeToDelete functionality -> check InvesmentNotes project
    LazyColumn {
        items(favoriteMovies.size) { movie ->
            MovieComponent(
                movie = favoriteMovies[movie],
                onClick = { /* Handle click */ }
            )
        }
    }
}