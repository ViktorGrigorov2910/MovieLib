package com.vgrigorov.movielib.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vgrigorov.movielib.domain.models.Movie

@Entity(tableName = "favorite_movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val rating: Double,
    val overview: String
) {
    fun toDomain() = Movie(
        id,
        title,
        posterPath,
        backdropPath,
        releaseDate,
        rating,
        overview
    )
}