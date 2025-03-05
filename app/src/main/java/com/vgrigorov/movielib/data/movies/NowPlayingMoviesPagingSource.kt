package com.vgrigorov.movielib.data.movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vgrigorov.movielib.data.MoviesAPI
import com.vgrigorov.movielib.data.resources.toDomain
import com.vgrigorov.movielib.domain.models.Movie
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NowPlayingMoviesPagingSource @Inject constructor(
    private val movieApiService: MoviesAPI,
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1 // Start from page 1 if no key is provided
            val response = movieApiService.getNowPlayingMovies(page = page).toDomain()

            LoadResult.Page(
                data = response.movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.movies.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}