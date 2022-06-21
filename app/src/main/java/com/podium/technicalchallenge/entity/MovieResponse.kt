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
data class Director(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
) : Parcelable

@Parcelize
data class Cast(
    @SerializedName("name")
    val name: String,
    @SerializedName("profilePath")
    val imageUrl: String,
    @SerializedName("character")
    val character: String,
    @SerializedName("order")
    val order: Int,
) : Parcelable

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
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("director")
    val director: Director,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("cast")
    val cast: List<Cast>
) : Parcelable
