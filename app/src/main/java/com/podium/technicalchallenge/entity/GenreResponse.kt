package com.podium.technicalchallenge.entity

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("data")
    val data: Genres
)

data class Genres(
    @SerializedName("genres")
    val genres: List<String>
)
