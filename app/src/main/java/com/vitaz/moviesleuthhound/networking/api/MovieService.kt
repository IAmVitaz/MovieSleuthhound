package com.vitaz.moviesleuthhound.networking.api

import com.vitaz.moviesleuthhound.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("/")
    suspend fun getMovies(
//        @Query("apikey") apiKey: String = "bee99c7",
        @Query("t") movieName: String,
        @Query("y") movieYear: String
    ): Response<Movie>
}
