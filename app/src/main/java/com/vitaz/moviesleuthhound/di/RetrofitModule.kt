package com.vitaz.moviesleuthhound.di

import com.vitaz.moviesleuthhound.networking.Interceptors.AuthInterceptor
import com.vitaz.moviesleuthhound.networking.api.MovieService
import com.vitaz.moviesleuthhound.networking.repository.RepositoryImpl
import com.vitaz.moviesleuthhound.networking.repository.RepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://www.omdbapi.com"

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule{

    @Singleton
    @Provides
    fun provideMovieService():MovieService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build())

        .build()
        .create(MovieService::class.java)

    @Singleton
    @Provides
    fun provideRepository(movieService:MovieService) : RepositoryInterface = RepositoryImpl(movieService)

}
