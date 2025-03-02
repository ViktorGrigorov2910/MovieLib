package com.vgrigorov.movielib.data.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vgrigorov.movielib.data.MoviesAPI
import com.vgrigorov.movielib.domain.models.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val movieApi: MoviesAPI
) : SearchRepositoryContract {

    override fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, // Number of items per page
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MovieSearchPagingSource(movieApi, query)
            }
        ).flow
    }
}