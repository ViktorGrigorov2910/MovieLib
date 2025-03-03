package com.vgrigorov.movielib.data.movies

import com.vgrigorov.movielib.data.MoviesAPI
import com.vgrigorov.movielib.data.resources.toDomain
import com.vgrigorov.movielib.domain.models.MoviesList
import javax.inject.Inject


class MoviesRepository @Inject constructor(
    private val moviesAPI: MoviesAPI
) : MoviesRepositoryContract {

    override suspend fun getNowPlayingMovies():MoviesList =
        moviesAPI.getNowPlayingMovies().toDomain()


    override suspend fun getPopularMovies(): MoviesList =
        moviesAPI.getPopularMovies().toDomain()


    override suspend fun getTopRatedMovies(): MoviesList =
        moviesAPI.getTopRatedMovies().toDomain()

}