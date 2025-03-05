package com.vgrigorov.movielib.data.movies

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vgrigorov.movielib.data.MoviesAPI
import com.vgrigorov.movielib.domain.models.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class MoviesRepository @Inject constructor(
    private val moviesAPI: MoviesAPI
) : MoviesRepositoryContract {

    override suspend fun getNowPlayingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, // Number of items per page
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                NowPlayingMoviesPagingSource(moviesAPI)
            }
        ).flow
    }


    override suspend fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, // Number of items per page
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PopularMoviesPagingSource(moviesAPI)
            }
        ).flow
    }


    override suspend fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, // Number of items per page
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                TopRatedMoviesPagingSource(moviesAPI)
            }
        ).flow
    }

    override suspend fun getMovieThrailer(movieId: Int): String =
        moviesAPI.getMovieVideos(movieId).results.find { it.type == "Trailer" && it.site == "YouTube" }
            .let { it ->
                return@let "https://www.youtube.com/watch?v=${it?.key}"
            }

}