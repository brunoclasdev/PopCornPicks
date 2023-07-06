package br.com.bclas.popcornpicks.presentation.state

import br.com.bclas.popcornpicks.presentation.model.ListMovieModel
import br.com.bclas.popcornpicks.presentation.model.MovieModel

internal sealed class UiState {
    internal object Loading : UiState()
    internal object Error : UiState()
    internal data class Failure(val throwable: Throwable? = null) : UiState()
}

internal sealed class MovieUiState : UiState(){
    internal data class NowPlayingSuccess constructor(val data: ListMovieModel) : MovieUiState()
    internal data class PopularSuccess constructor(val data: ListMovieModel) : MovieUiState()
    internal data class UpcomingSuccess constructor(val data: ListMovieModel) : MovieUiState()
    internal data class TopRatedSuccess constructor(val data: List<MovieModel>) : MovieUiState()

}
