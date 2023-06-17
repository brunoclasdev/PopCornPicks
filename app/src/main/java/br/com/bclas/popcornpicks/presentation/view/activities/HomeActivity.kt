package br.com.bclas.popcornpicks.presentation.view.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import br.com.bclas.popcornpicks.databinding.ActivityHomeBinding
import br.com.bclas.popcornpicks.presentation.util.ext.getScope
import org.koin.core.scope.Scope
import br.com.bclas.popcornpicks.framework.di.NAMED_MOVIE_hOME
import br.com.bclas.popcornpicks.presentation.adapter.ListMoviesAdapter
import br.com.bclas.popcornpicks.presentation.events.OnItemClickListener
import br.com.bclas.popcornpicks.presentation.model.ListMovieModel
import br.com.bclas.popcornpicks.presentation.model.MovieModel
import br.com.bclas.popcornpicks.presentation.state.UiState
import br.com.bclas.popcornpicks.presentation.util.ext.byViewModel
import br.com.bclas.popcornpicks.presentation.view.component.carousel.CarouselAdapter
import br.com.bclas.popcornpicks.presentation.view.component.carousel.ItemsIndicator
import br.com.bclas.popcornpicks.presentation.view.fragments.DetailMovieBottomSheetDialogFragment
import br.com.bclas.popcornpicks.presentation.viewmodel.PopCornPicksListViewModel
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity(), OnItemClickListener {

    companion object{
        const val TYPE_TOP_RATED = "top_rated"
        const val TYPE_NOW_PLAYING = "now_playing"
        const val TYPE_POPULAR = "popular"
        const val TYPE_UPCOMING = "upcoming"
    }

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val scope: Scope by lazy {
        getScope(NAMED_MOVIE_hOME)
    }

    private val homeViewModel: PopCornPicksListViewModel by scope.byViewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.lifecycleOwner = this
        listMovieTopRatedStateFlowObserver(TYPE_TOP_RATED)
        listMovieNowPlayingStateFlowObserver(TYPE_NOW_PLAYING)
        listMoviePopularStateFlowObserver(TYPE_POPULAR)
    }

    private fun listMovieTopRatedStateFlowObserver(type:String){
        lifecycleScope.launch {
            homeViewModel.uiState(type).collect {
                when (it) {
                    is UiState.Success -> {
                        fillCarouselView(it.data)
                    }

                    is UiState.Loading -> {
                        Toast.makeText(this@HomeActivity, "Carregando", Toast.LENGTH_SHORT).show()
                    }

                    is UiState.Error -> {
                        Toast.makeText(this@HomeActivity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun listMovieNowPlayingStateFlowObserver(type: String) {
        lifecycleScope.launch {
            homeViewModel.uiState(type).collect {
                when (it) {
                    is UiState.Success -> {
                        binding.lblNowPlaying.text = "Agora nos cinemas!"
                        fillListMoviesNowPlaying(it.data)
                    }

                    is UiState.Loading -> {
                        Toast.makeText(this@HomeActivity, "Carregando", Toast.LENGTH_SHORT).show()
                    }

                    is UiState.Error -> {
                        Toast.makeText(this@HomeActivity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun listMoviePopularStateFlowObserver(type: String) {
        lifecycleScope.launch {
            homeViewModel.uiState(type).collect {
                when (it) {
                    is UiState.Success -> {
                        binding.lblPopular.text = "Mais assistidos!"
                        fillListMoviesPopular(it.data)
                    }

                    is UiState.Loading -> {
                        Toast.makeText(this@HomeActivity, "Carregando", Toast.LENGTH_SHORT)
                            .show()
                    }

                    is UiState.Error -> {
                        Toast.makeText(this@HomeActivity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun fillCarouselView(data: ListMovieModel) {
        data?.let {
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.carouselView.layoutManager = layoutManager
            var topRated = it.results.filter { movie ->
                movie.voteAverage!! > 8.5
            }
            val adapter = CarouselAdapter(topRated, this)
            binding.carouselView.adapter = adapter
            binding.carouselView.addItemDecoration(ItemsIndicator())
        }
        PagerSnapHelper().attachToRecyclerView(binding.carouselView)

    }


    private fun fillListMoviesNowPlaying(data: ListMovieModel) {
        data?.let {
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.listMoviesNowPlaying.layoutManager = layoutManager
            val adapter = ListMoviesAdapter(it, this)
            binding.listMoviesNowPlaying.adapter = adapter
        }
    }

    private fun fillListMoviesPopular(data: ListMovieModel) {
        data?.let {
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.listMoviesPopular.layoutManager = layoutManager
            val adapter = ListMoviesAdapter(it, this)
            binding.listMoviesPopular.adapter = adapter
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        scope.close()
    }

    override fun onItemClick(item: MovieModel) {
        val bundle = Bundle()
        bundle.putParcelable("movie", item)
        val fragment = DetailMovieBottomSheetDialogFragment.newInstance
        fragment.arguments = bundle
        fragment.show(supportFragmentManager, "DetailMovieBottomSheetDialogFragment")
    }
}