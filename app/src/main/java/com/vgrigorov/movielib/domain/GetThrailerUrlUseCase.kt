package com.vgrigorov.movielib.domain

import com.vgrigorov.movielib.data.movies.MoviesRepositoryContract
import com.vgrigorov.movielib.domain.base.DataResult
import com.vgrigorov.movielib.domain.base.UseCase
import com.vgrigorov.movielib.domain.base.asDataResult
import javax.inject.Inject



class GetThrailerUrlUseCase @Inject constructor(
    private val repository: MoviesRepositoryContract
) : UseCase<Int, DataResult<String>> {

    /**
     * [param] is the movieID
     * */
    override suspend fun execute(param: Int): DataResult<String> = asDataResult {
        repository.getMovieThrailer(param)
    }


}