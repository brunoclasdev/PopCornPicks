package br.com.bclas.popcornpicks.data.model

import br.com.bclas.popcornpicks.domain.entity.MovieEntity
import com.google.gson.annotations.SerializedName

internal data class MovieModel(
    @SerializedName("adult")
    var adult: Boolean? = false,

    @SerializedName("backdrop_path")
    var backgroundImage: String? = null,

    @SerializedName("genre_ids")
    var idGenre: List<Int>? = listOf(0),

    @SerializedName("id")
    var id: Long? = 0,

    @SerializedName("original_language")
    var originalLanguage: String? = null,

    @SerializedName("original_title")
    var originalTitle : String? = null,

    @SerializedName("overview")
    var description : String? = null,

    @SerializedName("popularity")
    var popularity : Float? = 0.0f,

    @SerializedName("poster_path")
    var posterPath : String? = "",

    @SerializedName("release_date")
    var releaseDate : String? = "",

    @SerializedName("title")
    var title : String? = "",

    @SerializedName("video")
    var video : Boolean? = false,

    @SerializedName("vote_average")
    var voteAverage : Float? = 0.0f,

    @SerializedName("vote_count")
    var voteCount : Long? = 0,
)

internal fun MovieModel.toEntity(): MovieEntity {
    return MovieEntity(
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