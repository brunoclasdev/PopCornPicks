package br.com.bclas.popcornpicks.presentation.view.component.carousel

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import br.com.bclas.popcornpicks.databinding.CarouselBinding
import br.com.bclas.popcornpicks.presentation.model.MovieModel

class Carousel(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

    private val binding : CarouselBinding
    private val selectAdapter : CarouselAdapter

    init {
        binding = CarouselBinding.inflate(LayoutInflater.from(context), this, false)
        selectAdapter = CarouselAdapter(arrayListOf())
        setup(context)
    }

    private fun setup(context: Context){
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            addItemDecoration(ItemsIndicator())
            adapter = selectAdapter
        }
        PagerSnapHelper().attachToRecyclerView(binding.recyclerView)
    }

    fun setItemList(items: ArrayList<MovieModel>){
        selectAdapter.setItemList(items)
    }

}