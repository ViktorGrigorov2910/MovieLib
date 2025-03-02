package com.vgrigorov.movielib.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class MoviesList(
    val page: Int,
    val movies: List<Movie>
)

@Parcelize
data class Movie(
    val id : Int,
    val title: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val rating: Double?,
    val overview: String?
) : Parcelable