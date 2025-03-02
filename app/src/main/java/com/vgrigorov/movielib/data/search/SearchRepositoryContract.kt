package com.vgrigorov.movielib.data.search

import androidx.paging.PagingData
import com.vgrigorov.movielib.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface SearchRepositoryContract {

    fun searchMovies(query: String): Flow<PagingData<Movie>>

}