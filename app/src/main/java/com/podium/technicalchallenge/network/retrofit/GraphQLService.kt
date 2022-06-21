package com.podium.technicalchallenge.network.retrofit

import com.podium.technicalchallenge.entity.GenreResponse
import com.podium.technicalchallenge.entity.MovieResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface GraphQLService {
    @Headers("Content-Type: application/json")
    @POST("graphql")
    suspend fun postGetMovies(@Body body: String): Response<MovieResponse>

    @Headers("Content-Type: application/json")
    @POST("graphql")
    suspend fun postGetGenres(@Body body: String): Response<GenreResponse>
}
