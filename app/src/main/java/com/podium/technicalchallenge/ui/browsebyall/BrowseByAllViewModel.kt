package com.podium.technicalchallenge.ui.browsebyall

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.entity.MovieEntity
import com.podium.technicalchallenge.repos.Repo
import com.podium.technicalchallenge.repos.Result
import com.podium.technicalchallenge.ui.OrderBy
import com.podium.technicalchallenge.ui.Sort
import com.podium.technicalchallenge.ui.SortableViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class BrowseByAllViewModel @Inject constructor(
    private val repo: Repo
) : SortableViewModel() {
    private val _movies = MutableLiveData<List<MovieEntity>>()
    val movies: LiveData<List<MovieEntity>> = _movies

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    override fun onSortFieldsChanged(sort: Sort, orderBy: OrderBy) {
        getMovies(sort, orderBy)
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
