package com.podium.technicalchallenge.network.queries

import com.podium.technicalchallenge.ui.OrderBy
import com.podium.technicalchallenge.ui.Sort

object Queries {
    fun getTopFiveMovies() =
        """
query GetTopFiveMovies {
    movies(sort: DESC, orderBy: "voteAverage", limit: 5) {
        id
        title
        voteAverage
        posterPath
        genres
        director {
            id
            name
        }
        overview
        cast {
            profilePath
            name
            character
            order
        }
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
        voteAverage
        posterPath
        genres
        director {
            id
            name
        }
        overview
        cast {
            profilePath
            name
            character
            order
        }
    }
}
"""

    fun getMoviesByGenre(genre: String, sort: Sort, orderBy: OrderBy) =
        """
query GetMoviesByGenre {
    movies(genre: "$genre", sort: $sort, orderBy: "${orderBy.fieldName}") {
        id
        title
        voteAverage
        posterPath
        genres
        director {
            id
            name
        }
        overview
        cast {
            profilePath
            name
            character
            order
        }
    }
}
"""

    fun getMovies() =
        """
query GetMovies {
    movies {
                id
        title
        voteAverage
        posterPath
        genres
        director {
            id
            name
        }
        overview
        cast {
            profilePath
            name
            character
            order
        }
    }
}
"""

    fun getMovies(sort: Sort, orderBy: OrderBy) =
        """
query GetMovies {
    movies(sort: $sort, orderBy: "${orderBy.fieldName}") {
        id
        title
        voteAverage
        posterPath
        genres
        director {
            id
            name
        }
        overview
        cast {
            profilePath
            name
            character
            order
        }
    }
}
"""
}
