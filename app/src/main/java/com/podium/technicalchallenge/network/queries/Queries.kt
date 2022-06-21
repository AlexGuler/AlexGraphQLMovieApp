package com.podium.technicalchallenge.network.queries

import com.podium.technicalchallenge.ui.genre.OrderBy
import com.podium.technicalchallenge.ui.genre.Sort

object Queries {
    fun getTopFiveMovies() =
        """
query GetTopFiveMovies {
    movies(sort: DESC, orderBy: "voteAverage", limit: 5) {
        id
        title
        posterPath
        voteAverage
    }
}
"""

    fun getGenres() =
        """
query GetGenres {
    genres
}
"""

    fun getMoviesByGenre(genre: String) =
        """
query GetMoviesByGenre {
    movies(genre: "$genre") {
        id
        title
        posterPath
        voteAverage
    }
}
"""

    fun getMoviesByGenre(genre: String, sort: Sort, orderBy: OrderBy) =
        """
query GetMoviesByGenre {
    movies(genre: "$genre", sort: $sort, orderBy: "${orderBy.fieldName}") {
        id
        title
        posterPath
        voteAverage
    }
}
"""

    fun getMovies() =
        """
query GetMovies {
    movies {
        id
        title
        posterPath
        voteAverage
    }
}
"""

    fun getMovies(sort: Sort, orderBy: OrderBy) =
        """
query GetMoviesByGenre {
    movies(sort: $sort, orderBy: "${orderBy.fieldName}") {
        id
        title
        posterPath
        voteAverage
    }
}
"""
}
