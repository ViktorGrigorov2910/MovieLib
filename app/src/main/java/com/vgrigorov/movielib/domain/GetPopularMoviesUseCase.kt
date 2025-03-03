package com.vgrigorov.movielib.domain

import com.vgrigorov.movielib.data.movies.MoviesRepositoryContract
import com.vgrigorov.movielib.domain.base.DataResult
import com.vgrigorov.movielib.domain.base.UseCase
import com.vgrigorov.movielib.domain.base.asDataResult
import com.vgrigorov.movielib.domain.models.MoviesList
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MoviesRepositoryContract
) : UseCase<Unit, DataResult<MoviesList>> {

    override suspend fun execute(param: Unit): DataResult<MoviesList> = asDataResult {
        repository.getPopularMovies()
    }


}

