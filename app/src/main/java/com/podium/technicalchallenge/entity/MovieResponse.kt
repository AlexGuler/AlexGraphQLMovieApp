package com.podium.technicalchallenge.entity

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("data")
    val data: Movies
)

data class Movies(
    @SerializedName("movies")
    val movies: List<MovieEntity>
)

data class MovieEntity(
    @SerializedName("title")
    val title: String,
    @SerializedName("releaseDate")
    val releaseDate: String
)
