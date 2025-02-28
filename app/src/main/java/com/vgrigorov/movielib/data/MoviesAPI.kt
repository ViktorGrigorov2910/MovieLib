package com.vgrigorov.movielib.data

import com.vgrigorov.movielib.data.resources.MoviesListResource
import com.vgrigorov.movielib.di.BASE_URL
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesAPI {

    @GET("$BASE_URL/discover/movie")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean? = false,
        @Query("include_video") includeVideo: Boolean?= false,
    ): MoviesListResource


}