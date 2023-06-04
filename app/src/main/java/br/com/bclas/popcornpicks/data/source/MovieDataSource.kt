package br.com.bclas.popcornpicks.data.source

import br.com.bclas.popcornpicks.data.model.ListMovieModel
import retrofit2.Response

internal interface MovieDataSource {
    suspend fun getMovies(url: String): Response<ListMovieModel>
}