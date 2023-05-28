package br.com.bclas.popcornpicks.domain.entity

internal data class MovieEntity(
    val adult: Boolean?,
    val backgroundImage: String?,
    val idGenre: List<Int>?,
    val id: Long?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val description: String?,
    val popularity: Float?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Float?,
    val voteCount: Long?
)
