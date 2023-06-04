package br.com.bclas.popcornpicks.data.service

import br.com.bclas.popcornpicks.data.model.ListMovieModel
import br.com.bclas.popcornpicks.data.util.WEBSERVICE_NOW_PLAYING
import retrofit2.Response
import retrofit2.http.GET

internal interface MovieService {
    @GET(WEBSERVICE_NOW_PLAYING)
    suspend fun listMovie(): Response<ListMovieModel>
}