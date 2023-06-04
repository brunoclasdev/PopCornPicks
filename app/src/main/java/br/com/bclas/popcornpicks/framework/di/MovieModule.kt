package br.com.bclas.popcornpicks.framework.di

import br.com.bclas.popcornpicks.data.repository.MovieRepositoryImpl
import br.com.bclas.popcornpicks.data.source.MovieDataSource
import br.com.bclas.popcornpicks.domain.repository.MovieRepository
import br.com.bclas.popcornpicks.domain.usecase.GetMovieUseCase
import br.com.bclas.popcornpicks.domain.usecase.GetMovieUseCaseImpl
import br.com.bclas.popcornpicks.framework.connector.RetrofitConnector
import br.com.bclas.popcornpicks.framework.connector.RetrofitConnectorImpl
import br.com.bclas.popcornpicks.framework.data.source.MovieDataSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

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

}

fun loadMovieModules() {
    loadKoinModules(movieModule)
}

fun unloadMovieModules() {
    unloadKoinModules(movieModule)
}
