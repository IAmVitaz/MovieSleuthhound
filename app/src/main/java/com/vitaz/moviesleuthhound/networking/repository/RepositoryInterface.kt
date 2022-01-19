package com.vitaz.moviesleuthhound.networking.repository

import com.vitaz.moviesleuthhound.model.Movie
import com.vitaz.moviesleuthhound.networking.Resource

interface RepositoryInterface {
    suspend fun getMovie(movieName: String, movieYear: String): Resource<Movie>
}
