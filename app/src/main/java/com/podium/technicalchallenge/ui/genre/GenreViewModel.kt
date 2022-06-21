package com.podium.technicalchallenge.ui.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.entity.MovieEntity
import com.podium.technicalchallenge.repos.Repo
import com.podium.technicalchallenge.repos.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val repo: Repo
) : ViewModel() {

    lateinit var genre: String

    private val _genres = MutableLiveData<List<String>>()
    val genres: LiveData<List<String>> = _genres

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val _associatedMovies = MutableLiveData<List<MovieEntity>>()
    val associatedMovies: LiveData<List<MovieEntity>> = _associatedMovies

    private val _associatedLoading = MutableLiveData<Boolean>()
    val associatedLoading: LiveData<Boolean> = _associatedLoading

    private val _associatedError = MutableLiveData<Boolean>()
    val associatedError: LiveData<Boolean> = _associatedError

    private val sortFlow = MutableStateFlow<Sort?>(null)
    private val orderByFlow = MutableStateFlow<OrderBy?>(null)

    init {
        viewModelScope.launch {
            sortFlow.combine(orderByFlow) { sort, orderBy ->
                if (sort != null && orderBy != null) {
                    sort to orderBy
                } else {
                    null
                }
            }
                .filterNotNull()
                .collectLatest { (sort, orderBy) ->
                    getMoviesByGenre(genre, sort, orderBy)
                }
        }
    }

    fun onSortByChanged(newSort: String) {
        viewModelScope.launch {
            sortFlow.emit(
                when (newSort) {
                    "ASC" -> Sort.ASC
                    "DESC" -> Sort.DESC
                    else -> null
                }
            )
        }
    }

    fun onOrderByChanged(newOrderBy: String) {
        viewModelScope.launch {
            orderByFlow.emit(
                when (newOrderBy) {
                    "Title" -> OrderBy.TITLE
                    "Popularity" -> OrderBy.POPULARITY
                    "Vote Average" -> OrderBy.VOTE_AVERAGE
                    "Vote Count" -> OrderBy.VOTE_COUNT
                    "Original Title" -> OrderBy.ORIGINAL_TITLE
                    "Budget" -> OrderBy.BUDGET
                    "Runtime" -> OrderBy.RUNTIME
                    "Release Date" -> OrderBy.RELEASE_DATE
                    "Director" -> OrderBy.DIRECTOR
                    else -> null
                }
            )
        }
    }

    fun getGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            _error.postValue(false)
            val result = try {
                repo.getGenres()
            } catch (e: Exception) {
                Result.Error(e)
            }
            _loading.postValue(false)
            when (result) {
                is Result.Success<List<String>> -> {
                    _genres.postValue(result.data)
                }
                else -> {
                    _error.postValue(true)
                }
            }
        }
    }

    fun getMoviesByGenre(genre: String, sort: Sort? = null, orderBy: OrderBy? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            _associatedLoading.postValue(true)
            _associatedError.postValue(false)
            val result = try {
                repo.getMoviesByGenre(genre, sort, orderBy)
            } catch (e: Exception) {
                Result.Error(e)
            }
            _associatedLoading.postValue(false)
            when (result) {
                is Result.Success<List<MovieEntity>> -> {
                    _associatedMovies.postValue(result.data)
                }
                else -> {
                    _associatedError.postValue(true)
                }
            }
        }
    }

    companion object {
        private const val TAG = "GenreViewModel"
    }
}

enum class Sort {
    ASC,
    DESC
}

enum class OrderBy(val fieldName: String) {
    TITLE("title"),
    POPULARITY("popularity"),
    VOTE_AVERAGE("voteAverage"),
    VOTE_COUNT("voteCount"),
    ORIGINAL_TITLE("originalTitle"),
    BUDGET("budget"),
    RUNTIME("runtime"),
    RELEASE_DATE("releaseDate"),
    DIRECTOR("director"),
}
