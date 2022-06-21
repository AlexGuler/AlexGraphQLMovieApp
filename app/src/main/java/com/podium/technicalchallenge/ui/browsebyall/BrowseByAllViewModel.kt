package com.podium.technicalchallenge.ui.browsebyall

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.entity.MovieEntity
import com.podium.technicalchallenge.repos.Repo
import com.podium.technicalchallenge.repos.Result
import com.podium.technicalchallenge.ui.genre.OrderBy
import com.podium.technicalchallenge.ui.genre.Sort
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@HiltViewModel
class BrowseByAllViewModel @Inject constructor(
    private val repo: Repo
) : ViewModel() {
    private val _movies = MutableLiveData<List<MovieEntity>>()
    val movies: LiveData<List<MovieEntity>> = _movies

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

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
                    getMovies(sort, orderBy)
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

    fun getMovies(sort: Sort? = null, orderBy: OrderBy? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            _error.postValue(false)
            val result = try {
                repo.getMovies(sort, orderBy)
            } catch (e: Exception) {
                Result.Error(e)
            }
            _loading.postValue(false)
            when (result) {
                is Result.Success<List<MovieEntity>> -> {
                    _movies.postValue(result.data)
                }
                else -> {
                    _error.postValue(true)
                }
            }
        }
    }

    companion object {
        private const val TAG = "BrowseByAllViewModel"
    }
}
