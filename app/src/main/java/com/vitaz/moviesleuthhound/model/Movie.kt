package com.vitaz.moviesleuthhound.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("Title") val title: String?,
    @SerializedName("Released") val released: String?,
    @SerializedName("Poster") val poster: String?,
    @SerializedName("Response") val response: Boolean,
    @SerializedName("Genre") val genre: String?,
    @SerializedName("Plot") val plot: String?,
    @SerializedName("Error") val error: String?
    )
