package com.vgrigorov.movielib.data

import com.vgrigorov.movielib.domain.models.MoviesList

interface MoviesRepositoryContract {

    suspend fun getNowPlayingMovies(): List<MoviesList>

    suspend fun getPopularMovies(): MoviesList

    suspend fun getTopRatedMovies(): List<MoviesList>

}
