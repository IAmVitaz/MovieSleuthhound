package com.vitaz.moviesleuthhound.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitaz.moviesleuthhound.model.Movie
import com.vitaz.moviesleuthhound.networking.Resource
import com.vitaz.moviesleuthhound.networking.repository.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    val repository: RepositoryInterface
): ViewModel() {

    sealed class State{
        class Success(val movie: Movie):State()
        class Failure(val errorText:String):State()
        object Loading:State()
        object Empty:State()
    }

    private val _conversion = MutableStateFlow<State>(State.Empty)
    val conversion: StateFlow<State> = _conversion

    fun getMovie(){
        viewModelScope.launch(Dispatchers.IO) {
            _conversion.value = State.Loading
            when(val movieResponse = repository.getMovie("star", "2021")) {
                is Resource.Error -> _conversion.value = State.Failure(movieResponse.message ?: "Unexpected Error")
                is Resource.Success -> {
                    if (movieResponse.data != null) {
                        _conversion.value = State.Success(movieResponse.data)
                    } else {
                        _conversion.value = State.Failure("No movies found meeting set criteria")
                    }
                }
            }
        }
    }
}
