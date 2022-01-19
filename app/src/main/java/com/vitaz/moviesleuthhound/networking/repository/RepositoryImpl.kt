package com.vitaz.moviesleuthhound.networking.repository

import com.vitaz.moviesleuthhound.model.Movie
import com.vitaz.moviesleuthhound.networking.Resource
import com.vitaz.moviesleuthhound.networking.api.MovieService
import java.lang.Exception
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    val movieService: MovieService
): RepositoryInterface {
    override suspend fun getMovie(movieName: String, movieYear: String): Resource<Movie> {
        return try {
            val response = movieService.getMovies(movieName = movieName, movieYear = movieYear)
            when (val result: Movie? = response.body()) {
                null -> {
                    return Resource.Error("Empty response")
                }
                else -> {
                    if (response.isSuccessful && result.response){
                        Resource.Success(result)
                    }else{
                        Resource.Error("${result.error}")
                    }
                }
            }

        }catch (e: Exception){
            println("movieApi $e")
            Resource.Error("An Error occurred: $e")
        }
    }
}
