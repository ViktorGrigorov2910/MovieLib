package com.vgrigorov.movielib.data

import com.vgrigorov.movielib.data.resources.Movie
import javax.inject.Inject


class MoviesRepository @Inject constructor(
    private val moviesAPI: MoviesAPI
) : MoviesRepositoryContract {

    override suspend fun getNowPlayingMovies(): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun getPopularMovies(): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun getTopRatedMovies(): List<Movie> {
        TODO("Not yet implemented")
    }
}