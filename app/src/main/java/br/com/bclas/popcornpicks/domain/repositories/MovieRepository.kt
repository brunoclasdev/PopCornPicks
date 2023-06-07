package br.com.bclas.popcornpicks.domain.repositories

import br.com.bclas.popcornpicks.domain.entity.ListMovieEntity
import br.com.bclas.popcornpicks.domain.util.Result

internal interface MovieRepository {
    suspend fun getMovies(type: String): Result<ListMovieEntity, Unit>

}