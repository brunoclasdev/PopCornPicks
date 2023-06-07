package br.com.bclas.popcornpicks.framework.data.source

import br.com.bclas.popcornpicks.data.model.ListMovieModel
import br.com.bclas.popcornpicks.data.service.MovieService
import br.com.bclas.popcornpicks.data.source.MovieDataSource
import br.com.bclas.popcornpicks.framework.connector.RetrofitConnector
import br.com.bclas.popcornpicks.framework.util.BASE_URL
import retrofit2.Response

internal class MovieDataSourceImpl(
    private val retrofitConnector: RetrofitConnector
) : MovieDataSource {

    private val movieService : MovieService by lazy { retrofitConnector
        .baseUrl(BASE_URL)
        .create(MovieService::class.java)}

    override suspend fun getMovies(type: String): Response<ListMovieModel> {
        return movieService.listMovie(type)
    }
}