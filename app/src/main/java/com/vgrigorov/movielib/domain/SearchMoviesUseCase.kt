package com.vgrigorov.movielib.domain

import androidx.paging.PagingData
import com.vgrigorov.movielib.data.search.SearchRepositoryContract
import com.vgrigorov.movielib.domain.base.UseCase
import com.vgrigorov.movielib.domain.models.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMoviesUseCase  @Inject constructor(
    private val repository: SearchRepositoryContract
) : UseCase<String, Flow<PagingData<Movie>>> {


    /**
     * [param] - query String that is used for the search
     * */
    override suspend fun execute(param: String): Flow<PagingData<Movie>> = repository.searchMovies(param)



}
