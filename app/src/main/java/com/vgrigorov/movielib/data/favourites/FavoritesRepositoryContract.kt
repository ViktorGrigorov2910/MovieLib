package com.vgrigorov.movielib.data.favourites

import com.vgrigorov.movielib.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface FavoritesRepositoryContract {

    suspend fun addMovie(movie: Movie)

    suspend fun removeMovie(movieId: Int)

    fun getAllMovies(): Flow<List<Movie>>


}