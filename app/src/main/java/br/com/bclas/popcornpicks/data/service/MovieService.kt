package br.com.bclas.popcornpicks.data.service

import br.com.bclas.popcornpicks.data.model.ListMovieModel
import br.com.bclas.popcornpicks.data.util.MOVIE
import br.com.bclas.popcornpicks.data.util.WEBSERVICE_NOW_PLAYING
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface MovieService {
    @GET("$MOVIE{type}$WEBSERVICE_NOW_PLAYING")
    suspend fun listMovie(@Path("type") type: String): Response<ListMovieModel>
}