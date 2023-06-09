package br.com.bclas.popcornpicks.presentation.events

import br.com.bclas.popcornpicks.presentation.model.MovieModel

interface OnItemClickListener {
    fun onItemClick(item: MovieModel)
}