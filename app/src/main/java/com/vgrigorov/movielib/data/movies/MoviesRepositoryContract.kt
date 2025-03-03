package com.vgrigorov.movielib.data.movies

import com.vgrigorov.movielib.domain.models.MoviesList

interface MoviesRepositoryContract {

    suspend fun getNowPlayingMovies(): MoviesList

    suspend fun getPopularMovies(): MoviesList

    suspend fun getTopRatedMovies(): MoviesList

}
