package com.vgrigorov.movielib.di

import android.content.Context
import com.vgrigorov.movielib.Keys
import com.vgrigorov.movielib.data.favourites.FavoritesRepository
import com.vgrigorov.movielib.data.MoviesAPI
import com.vgrigorov.movielib.data.movies.MoviesRepository
import com.vgrigorov.movielib.data.movies.MoviesRepositoryContract
import com.vgrigorov.movielib.data.search.SearchRepository
import com.vgrigorov.movielib.data.search.SearchRepositoryContract
import com.vgrigorov.movielib.database.MovieDao
import com.vgrigorov.movielib.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://api.themoviedb.org/3/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoviesAPI(retrofit: Retrofit): MoviesAPI = retrofit.create(MoviesAPI::class.java)

    @Provides
    @Singleton
    fun providesMoviesRepository(repo: MoviesRepository): MoviesRepositoryContract = repo


    @Provides
    @Singleton
    fun providesSearchRepository(repo: SearchRepository): SearchRepositoryContract = repo

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return MovieDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(movieDao: MovieDao): FavoritesRepository {
        return FavoritesRepository(movieDao)
    }


    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer " + Keys.API_ACCESS_TOKEN)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()


    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()

}