package com.vgrigorov.movielib.domain.models


data class MoviesList(
    val page: Int,
    val movies: List<Movie>
)


data class Movie(
    val title: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val rating: Double?,
    val overview: String?
)