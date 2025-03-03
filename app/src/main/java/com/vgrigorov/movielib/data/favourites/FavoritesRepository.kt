package com.vgrigorov.movielib.data.favourites

import com.vgrigorov.movielib.database.MovieDao
import com.vgrigorov.movielib.domain.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepository @Inject constructor(
    private val movieDao: MovieDao
) : FavoritesRepositoryContract {

    override suspend fun addMovie(movie: Movie) {
        movieDao.addMovie(movie.toEntity())
    }

    override suspend fun removeMovie(movieId: Int) {
        movieDao.removeMovie(movieId)
    }

    override fun getAllMovies(): Flow<List<Movie>> {
        return movieDao.getAllMovies().map { movieEntities ->
            movieEntities.map { it.toDomain() } // Convert each MovieEntity to Movie
        }
    }

    override fun isMovieFavorite(id: Int): Boolean {
        return movieDao.getFavoriteMovieById(id) != null // If the movie exists in the DB, it's a favorite
    }

}
