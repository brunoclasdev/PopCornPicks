package br.com.bclas.popcornpicks.presentation.state

import br.com.bclas.popcornpicks.presentation.model.ListMovieModel

internal sealed class UiState {
    internal object Loading : UiState()
    internal data class Success(val data: ListMovieModel) : UiState()
    internal object Error : UiState()
}