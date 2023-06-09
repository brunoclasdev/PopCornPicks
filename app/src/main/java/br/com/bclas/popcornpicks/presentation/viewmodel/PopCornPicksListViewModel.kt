package br.com.bclas.popcornpicks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.bclas.popcornpicks.domain.usecases.GetMovieUseCase
import br.com.bclas.popcornpicks.presentation.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import br.com.bclas.popcornpicks.domain.util.Result
import br.com.bclas.popcornpicks.presentation.model.MovieModel
import br.com.bclas.popcornpicks.presentation.model.toModel

internal class PopCornPicksListViewModel(private val getMovieUseCase: GetMovieUseCase) : ViewModel() {

    fun uiState (type: String) : StateFlow<UiState> = getMovieFlow(type).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState.Loading
    )

    private fun getMovieFlow(type: String): Flow<UiState> {
        return getMovieUseCase(type).map {
            when (it) {
                is Result.Success -> UiState.Success(it.value.toModel())
                is Result.Loading -> UiState.Loading
                else ->{UiState.Error}
            }
        }.catch {
            emit(UiState.Error)
        }
    }

}