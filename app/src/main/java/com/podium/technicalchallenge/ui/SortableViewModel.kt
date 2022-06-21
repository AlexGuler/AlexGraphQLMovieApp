package com.podium.technicalchallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

abstract class SortableViewModel : ViewModel() {

    private val sortFlow = MutableStateFlow<Sort?>(null)
    private val orderByFlow = MutableStateFlow<OrderBy?>(null)

    abstract fun onSortFieldsChanged(sort: Sort, orderBy: OrderBy)

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
                    onSortFieldsChanged(sort, orderBy)
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
