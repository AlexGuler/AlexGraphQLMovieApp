package com.podium.technicalchallenge.repos

import com.podium.technicalchallenge.entity.MovieEntity
import com.podium.technicalchallenge.network.queries.Queries
import com.podium.technicalchallenge.network.retrofit.GraphQLService
import com.podium.technicalchallenge.ui.OrderBy
import com.podium.technicalchallenge.ui.Sort
import com.podium.technicalchallenge.util.asJSONQueryString
import javax.inject.Inject

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

class Repo @Inject constructor(private val service: GraphQLService) {

    suspend fun getTopFiveMovies(): Result<List<MovieEntity>> {
        val response = service.postGetMovies(Queries.getTopFiveMovies().asJSONQueryString())
        val movies = response.body()?.data
        return if (movies != null) {
            Result.Success(movies.movies)
        } else {
            Result.Error(java.lang.Exception())
        }
    }

    suspend fun getGenres(): Result<List<String>> {
        val response = service.postGetGenres(Queries.getGenres().asJSONQueryString())
        val movies = response.body()?.data
        return if (movies != null) {
            Result.Success(movies.genres)
        } else {
            Result.Error(java.lang.Exception())
        }
    }

    suspend fun getMoviesByGenre(
        genre: String,
        sort: Sort?,
        orderBy: OrderBy?
    ): Result<List<MovieEntity>> {
        val response = service.postGetMovies(
            if (sort != null && orderBy != null) {
                Queries.getMoviesByGenre(genre, sort, orderBy).asJSONQueryString()
            } else {
                Queries.getMoviesByGenre(genre).asJSONQueryString()
            }
        )
        val movies = response.body()?.data
        return if (movies != null) {
            Result.Success(movies.movies)
        } else {
            Result.Error(java.lang.Exception())
        }
    }

    suspend fun getMovies(
        sort: Sort?,
        orderBy: OrderBy?
    ): Result<List<MovieEntity>> {
        val response = service.postGetMovies(
            if (sort != null && orderBy != null) {
                Queries.getMovies(sort, orderBy).asJSONQueryString()
            } else {
                Queries.getMovies().asJSONQueryString()
            }
        )
        val movies = response.body()?.data
        return if (movies != null) {
            Result.Success(movies.movies)
        } else {
            Result.Error(java.lang.Exception())
        }
    }
}
