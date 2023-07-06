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
import br.com.bclas.popcornpicks.presentation.state.MovieUiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.plus

internal class PopCornPicksListViewModel(private val getMovieUseCase: GetMovieUseCase) :
    ViewModel() {

    private val _movieUiStateFlow = MutableStateFlow<UiState?>(null)
    val movieUiStateFlow: StateFlow<UiState?> = _movieUiStateFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState.Loading
    )

    init {
        registerExceptionHandler()
    }

    fun getMovieNowPlayingFlow(type: String) {
        getMovieUseCase(type).onEach {
            _movieUiStateFlow.value = when (it) {
                is Result.Success -> MovieUiState.NowPlayingSuccess(it.value.toModel())
                is Result.Failure -> UiState.Failure(it.throwable)
                else -> {
                    UiState.Error
                }
            }
        }.onStart {
            _movieUiStateFlow.value = UiState.Loading
        }.catch {
            _movieUiStateFlow.value = UiState.Error
        }.launchIn(viewModelScope)
    }

    fun getMoviePopularFlow(type: String) {
        getMovieUseCase(type).onEach {
            _movieUiStateFlow.value = when (it) {
                is Result.Success -> MovieUiState.PopularSuccess(it.value.toModel())
                is Result.Failure -> UiState.Failure(it.throwable)
                else -> {
                    UiState.Error
                }
            }
        }.onStart {
            _movieUiStateFlow.value = UiState.Loading
        }.catch {
            _movieUiStateFlow.value = UiState.Error
        }.launchIn(viewModelScope)
    }

    fun getMovieTopRetaedFlow(type: String) {
        getMovieUseCase(type).onEach {
            _movieUiStateFlow.value = when (it) {
                is Result.Success -> {
                    var topRated = it.value.toModel().results.filter {
                        it.voteAverage!! > 8.5
                    }
                    MovieUiState.TopRatedSuccess(topRated)
                }
                is Result.Failure -> UiState.Failure(it.throwable)
                else -> {
                    UiState.Error
                }
            }
        }.onStart {
            _movieUiStateFlow.value = UiState.Loading
        }.catch {
            _movieUiStateFlow.value = UiState.Error
        }.launchIn(viewModelScope)
    }

    fun getMovieUpComingFlow(type: String) {
        getMovieUseCase(type).onEach {
            _movieUiStateFlow.value = when (it) {
                is Result.Success -> MovieUiState.UpcomingSuccess(it.value.toModel())
                is Result.Failure -> UiState.Failure(it.throwable)
                else -> {
                    UiState.Error
                }
            }
        }.onStart {
            _movieUiStateFlow.value = UiState.Loading
        }.catch {
            _movieUiStateFlow.value = UiState.Error
        }.launchIn(viewModelScope)
    }

    private fun registerExceptionHandler() {
        val coroutinesExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            _movieUiStateFlow.value = UiState.Failure(throwable)
        }
        viewModelScope.plus(coroutinesExceptionHandler)
    }
}