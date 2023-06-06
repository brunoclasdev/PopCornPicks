package br.com.bclas.popcornpicks.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.bclas.popcornpicks.databinding.ItemListMovieBinding
import br.com.bclas.popcornpicks.framework.util.BASE_URL_IMG
import br.com.bclas.popcornpicks.presentation.model.ListMovieModel
import coil.load
import coil.transform.RoundedCornersTransformation

class ListMoviesAdapter(private val listMovieModel : ListMovieModel) : RecyclerView.Adapter<ListMoviesAdapter.ListMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMovieViewHolder {
        val binding = ItemListMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListMovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listMovieModel.results.size
    }

    override fun onBindViewHolder(holder: ListMovieViewHolder, position: Int) {
        val currentItem = listMovieModel.results[position]
        holder.binding.apply {
            imageMovie.load(BASE_URL_IMG + currentItem.posterPath){
                crossfade(true)
                listener(
                    onStart = {
                        lottieAnimationMovieView.setAnimation("popcornpicks-load.json")
                    },
                    onSuccess = { _, _ ->
                        lottieAnimationMovieView.pauseAnimation()
                        lottieAnimationMovieView.visibility = View.GONE
                        imageMovie.load(BASE_URL_IMG + currentItem.posterPath){
                            crossfade(true)
                            transformations(RoundedCornersTransformation(30f))
                            size(500, 650)

                        }
                    },
                    onError = { _, _ ->
                        lottieAnimationMovieView.pauseAnimation()
                        lottieAnimationMovieView.visibility = View.GONE
                    }
                )
            }
        }
    }

    class ListMovieViewHolder(val binding: ItemListMovieBinding) : RecyclerView.ViewHolder(binding.root)

}