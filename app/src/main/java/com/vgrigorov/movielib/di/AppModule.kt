package com.vgrigorov.movielib.di

import com.vgrigorov.movielib.Keys
import com.vgrigorov.movielib.data.MoviesAPI
import com.vgrigorov.movielib.data.MoviesRepository
import com.vgrigorov.movielib.data.MoviesRepositoryContract
import com.vgrigorov.movielib.data.search.SearchRepository
import com.vgrigorov.movielib.data.search.SearchRepositoryContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

    // TODO: Change for local db
//    @Provides
//    fun providePlacesDatabaseDao(appDatabase: PlacesHistoryDatabaseDao): PlacesDatabaseDao {
//        return appDatabase.dao()
//    }
//
//    @Provides
//    @Singleton
//    fun providePlacesDatabase(@ApplicationContext context: Context): PlacesHistoryDatabaseDao {
//        return Room.databaseBuilder(
//            context.applicationContext,
//            PlacesHistoryDatabaseDao::class.java,
//            "places_list"
//        )
//            .fallbackToDestructiveMigration()
//            .build()
//    }
//
//    @Provides
//    fun provideDatabaseDao(appDatabase: UserHistoryDatabaseDao): HistoryDatabaseDao {
//        return appDatabase.dao()
//    }
//
//    @Provides
//    @Singleton
//    fun provideDatabase(@ApplicationContext context: Context): UserHistoryDatabaseDao {
//        return Room.databaseBuilder(
//            context.applicationContext,
//            UserHistoryDatabaseDao::class.java,
//            "history_list"
//        )
//            .fallbackToDestructiveMigration()
//            .build()
//    }


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