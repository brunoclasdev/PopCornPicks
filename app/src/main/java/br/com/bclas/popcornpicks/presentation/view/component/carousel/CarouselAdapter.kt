package br.com.bclas.popcornpicks.presentation.view.component.carousel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.bclas.popcornpicks.databinding.ItemListCarouselBinding
import br.com.bclas.popcornpicks.framework.util.BASE_URL_IMG
import br.com.bclas.popcornpicks.presentation.events.OnItemClickListener
import br.com.bclas.popcornpicks.presentation.model.MovieModel
import coil.load
import coil.transform.RoundedCornersTransformation

class CarouselAdapter(private var items: List<MovieModel>, private val listener: OnItemClickListener? = null) : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    private lateinit var binding: ItemListCarouselBinding
//    private lateinit var items: ArrayList<MovieModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        binding = ItemListCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItemList(items: ArrayList<MovieModel>) {
        this.items = items
        notifyDataSetChanged()
    }


    inner class CarouselViewHolder(val binding: ItemListCarouselBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieModel) {

            binding.apply {
                imageCarousel.load(BASE_URL_IMG + movie.posterPath) {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(0f))
                }
                imageCarousel.setOnClickListener {
                    listener?.let {
                        it.onItemClick(movie)
                    }
                }
            }
        }
    }
}