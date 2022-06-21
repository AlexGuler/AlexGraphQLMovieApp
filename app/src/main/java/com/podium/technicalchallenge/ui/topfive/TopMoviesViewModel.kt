package com.podium.technicalchallenge.ui.topfive

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
import kotlinx.coroutines.launch

@HiltViewModel
class TopMoviesViewModel @Inject constructor(
    private val repo: Repo
) : ViewModel() {
    private val _topMovies = MutableLiveData<List<MovieEntity>>()
    val topMovies: LiveData<List<MovieEntity>> = _topMovies

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    fun getTopFiveMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            _error.postValue(false)
            val result = try {
                repo.getTopFiveMovies()
            } catch (e: Exception) {
                Result.Error(e)
            }
            _loading.postValue(false)
            when (result) {
                is Result.Success<List<MovieEntity>> -> {
                    _topMovies.postValue(result.data)
                }
                else -> {
                    _error.postValue(true)
                }
            }
        }
    }

    companion object {
        private const val TAG = "DemoViewModel"
    }
}
