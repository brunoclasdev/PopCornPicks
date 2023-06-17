package br.com.bclas.popcornpicks.data.model

import br.com.bclas.popcornpicks.domain.entity.ListMovieEntity
import br.com.bclas.popcornpicks.domain.entity.MovieEntity
import com.google.gson.annotations.SerializedName

internal class ListMovieModel(
    @SerializedName("page")
    var page: Int? = 0,

    @SerializedName("results")
    var results: ArrayList<MovieModel> = arrayListOf(),

    @SerializedName("total_pages")
    var totalPage: Int? = 0,

    @SerializedName("total_results")
    var totalResults: Int? = 0
)

internal fun ListMovieModel.toEntity(): ListMovieEntity {
    return ListMovieEntity(
        page = this.page,
        results = this.results.map { it.toEntity() } as ArrayList<MovieEntity>,
        totalPage = this.totalPage,
        totalResults = this.totalResults
    )
}