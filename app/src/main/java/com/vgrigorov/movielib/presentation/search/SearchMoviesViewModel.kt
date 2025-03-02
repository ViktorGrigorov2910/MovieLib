package com.vgrigorov.movielib.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.vgrigorov.movielib.domain.SearchMoviesUseCase
import com.vgrigorov.movielib.domain.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchMoviesViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private var _search = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val search: State<Flow<PagingData<Movie>>> = _search


    var searchParam = mutableStateOf("")

    fun searchMovies(query: String) {
        viewModelScope.launch {
            if (query.isNotEmpty()) {
                _search.value = searchMoviesUseCase.execute(query).map { result ->
                    result.filter { ((it.posterPath != null)) }
                }.cachedIn(viewModelScope)
            }
        }
    }

}