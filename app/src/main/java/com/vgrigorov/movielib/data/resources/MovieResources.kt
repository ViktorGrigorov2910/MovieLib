package com.vgrigorov.movielib.data.resources

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.vgrigorov.movielib.domain.models.Movie
import com.vgrigorov.movielib.domain.models.MoviesList

@Keep
data class MovieResource(
    val id: Int,
    val title: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val rating: Double?,
    val overview: String?
)

@Keep
data class MoviesListResource(
    val page: Int,
    val results: List<MovieResource>
)


fun MoviesListResource.toDomain(): MoviesList = MoviesList(
    this.page,
    this.results.map { it.toDomain() }
)

fun MovieResource.toDomain(): Movie = Movie(
    this.id,
    this.title ?: "N/A",
    this.posterPath,
    this.backdropPath,
    this.releaseDate,
    this.rating,
    this.overview,

)