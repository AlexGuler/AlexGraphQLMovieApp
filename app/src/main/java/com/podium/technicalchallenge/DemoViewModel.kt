package com.podium.technicalchallenge

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.entity.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class DemoViewModel @Inject constructor(
    private val randomStr: String
) : ViewModel() {
    val TAG = "DemoViewModel"

    init {
        Log.d(TAG, "alex: randomstr: $randomStr")
    }

    fun getMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = try {
                Repo.getInstance().getMovies()
            } catch (e: Exception) {
                Result.Error(e)
            }
            when (result) {
                is Result.Success<List<MovieEntity>?> -> {

                    result.data?.forEach {
                        Log.d(TAG, "title: ${it.title}")
                    }
                    Log.d(TAG, "movies= " + result.data)
                    Log.d(TAG, "movies= " + result.data?.size)
                }
                else -> {
                    Log.e(TAG, "movies= " + result)
                }
            }
        }
    }
}
