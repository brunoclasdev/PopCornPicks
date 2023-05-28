package br.com.bclas.popcornpicks.presentation.model

import android.os.Parcelable
import br.com.bclas.popcornpicks.domain.entity.MovieEntity
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class MovieModel(

    var adult: Boolean? = false,

    var backgroundImage: String? = null,

    var idGenre: List<Int>? = listOf(0),

    var id: Long? = 0,

    var originalLanguage: String? = null,

    var originalTitle: String? = null,

    var description: String? = null,

    var popularity: Float? = 0.0f,

    var posterPath: String? = "",

    var releaseDate: String? = "",

    var title: String? = "",

    var video: Boolean? = false,

    var voteAverage: Float? = 0.0f,

    var voteCount: Long? = 0,
) : Parcelable

internal fun MovieEntity.toModel(): MovieModel {
    return MovieModel(
        adult = this.adult,
        backgroundImage = this.backgroundImage,
        idGenre = this.idGenre,
        id = this.id,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        description = this.description,
        popularity = this.popularity,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}