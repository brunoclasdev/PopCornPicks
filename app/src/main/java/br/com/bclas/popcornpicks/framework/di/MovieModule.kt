package br.com.bclas.popcornpicks.framework.di

import br.com.bclas.popcornpicks.data.repository.MovieRepositoryImpl
import br.com.bclas.popcornpicks.data.source.MovieDataSource
import br.com.bclas.popcornpicks.domain.repositories.MovieRepository
import br.com.bclas.popcornpicks.domain.usecases.GetMovieUseCase
import br.com.bclas.popcornpicks.domain.usecases.GetMovieUseCaseImpl
import br.com.bclas.popcornpicks.framework.connector.RetrofitConnector
import br.com.bclas.popcornpicks.framework.connector.RetrofitConnectorImpl
import br.com.bclas.popcornpicks.framework.data.source.MovieDataSourceImpl
import br.com.bclas.popcornpicks.presentation.viewmodel.PopCornPicksListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val NAMED_MOVIE_hOME : String = "NAMED_MOVIE_hOME"
private val movieModule: Module = module {

    //Inject Connector
    factory<RetrofitConnector> {
        RetrofitConnectorImpl(
            androidContext()
        )
    }

    //Inject DataSource
    factory<MovieDataSource> {
        MovieDataSourceImpl(
            retrofitConnector = get()
        )
    }

    //Inject Repository
    factory<MovieRepository> {
        MovieRepositoryImpl(
            movieDataSource = get()
        )
    }

    //Inject UseCase
    factory<GetMovieUseCase>{
        GetMovieUseCaseImpl(
            movieRepository = get()
        )
    }

    // Inject ViewModel trought scope
    scope(named(NAMED_MOVIE_hOME)){
        viewModel{
            PopCornPicksListViewModel(
                getMovieUseCase = get()
            )
        }
    }
}

fun loadMovieModules() {
    loadKoinModules(movieModule)
}

fun unloadMovieModules() {
    unloadKoinModules(movieModule)
}
