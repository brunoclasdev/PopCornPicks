package br.com.bclas.popcornpicks.domain.repository

import br.com.bclas.popcornpicks.domain.entity.ListMovieEntity
import br.com.bclas.popcornpicks.domain.util.Result

internal interface MovieRepository {
    suspend fun getMovies(): Result<ListMovieEntity, Unit>

}