package com.podium.technicalchallenge.ui.genre

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
class GenreViewModel @Inject constructor(
    private val repo: Repo
) : SortableViewModel() {

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

    override fun onSortFieldsChanged(sort: Sort, orderBy: OrderBy) {
        getMoviesByGenre(genre, sort, orderBy)
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
