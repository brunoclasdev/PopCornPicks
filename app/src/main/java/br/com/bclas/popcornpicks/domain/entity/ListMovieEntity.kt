package br.com.bclas.popcornpicks.domain.entity

internal data class ListMovieEntity(
    val page: Int? = 0,
    val results: List<MovieEntity> = listOf(),
    val totalPage: Int? = 0,
    val totalResults: Int? = 0
)
