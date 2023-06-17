package br.com.bclas.popcornpicks.presentation.model

import android.os.Parcelable
import br.com.bclas.popcornpicks.domain.entity.ListMovieEntity
import br.com.bclas.popcornpicks.domain.entity.MovieEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListMovieModel(
    var page: Int? = 0,
    var results: ArrayList<MovieModel> = arrayListOf(),
    var totalPage: Int? = 0,
    var totalResults: Int? = 0
): Parcelable
internal fun ListMovieEntity.toModel() : ListMovieModel {
    return ListMovieModel(
        page = this.page,
        results = this.results.map { it.toModel() } as ArrayList<MovieModel>,
        totalPage = this.totalPage,
        totalResults = this.totalResults
    )
}