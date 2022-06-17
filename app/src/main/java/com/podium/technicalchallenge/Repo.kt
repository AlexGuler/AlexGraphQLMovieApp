package com.podium.technicalchallenge

import com.podium.technicalchallenge.entity.MovieEntity
import com.podium.technicalchallenge.network.ApiClient
import com.podium.technicalchallenge.network.queries.Queries
import com.podium.technicalchallenge.network.retrofit.GraphQLService
import org.json.JSONObject

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

class Repo {

    suspend fun getMovies(): Result<List<MovieEntity>> {
        val paramObject = JSONObject()
        paramObject.put(
            "query", Queries.getMoviesQuery()
        )
        val response = ApiClient
            .getInstance()
            .provideRetrofitClient()
            .create(GraphQLService::class.java)
            .postGetMovies(paramObject.toString())
        val movies = response.body()?.data
        return if (movies != null) {
            Result.Success(movies.movies)
        } else {
            Result.Error(java.lang.Exception())
        }
    }

    companion object {
        private var INSTANCE: Repo? = null
        fun getInstance() = INSTANCE
            ?: Repo().also {
                INSTANCE = it
            }
    }
}
