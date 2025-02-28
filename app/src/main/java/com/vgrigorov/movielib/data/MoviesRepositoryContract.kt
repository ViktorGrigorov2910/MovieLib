package com.vgrigorov.movielib.data

import com.vgrigorov.movielib.data.resources.Movie

interface MoviesRepositoryContract {

    suspend fun getNowPlayingMovies(): List<Movie>

    suspend fun getPopularMovies(): List<Movie>

    suspend fun getTopRatedMovies(): List<Movie>

}
