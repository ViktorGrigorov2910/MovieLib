package com.vgrigorov.movielib.domain

import androidx.paging.PagingData
import com.vgrigorov.movielib.data.movies.MoviesRepositoryContract
import com.vgrigorov.movielib.domain.base.UseCase
import com.vgrigorov.movielib.domain.models.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val repository: MoviesRepositoryContract
) : UseCase<Unit, Flow<PagingData<Movie>>> {

    override suspend fun execute(param: Unit): Flow<PagingData<Movie>> =
        repository.getTopRatedMovies()


}