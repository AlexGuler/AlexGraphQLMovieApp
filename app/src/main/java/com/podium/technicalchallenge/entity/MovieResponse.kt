package com.podium.technicalchallenge.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MovieResponse(
    @SerializedName("data")
    val data: Movies
)

data class Movies(
    @SerializedName("movies")
    val movies: List<MovieEntity>
)

@Parcelize
data class MovieEntity(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("posterPath")
    val imageUrl: String,
    @SerializedName("voteAverage")
    val rating: Float,
) : Parcelable
