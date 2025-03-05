package com.vgrigorov.movielib.data.movies

import androidx.paging.PagingData
import com.vgrigorov.movielib.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepositoryContract {

    suspend fun getNowPlayingMovies(): Flow<PagingData<Movie>>

    suspend fun getPopularMovies(): Flow<PagingData<Movie>>

    suspend fun getTopRatedMovies(): Flow<PagingData<Movie>>
    suspend fun getMovieThrailer(movieId: Int): String

}
