package br.com.bclas.popcornpicks.presentation.view.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
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
import br.com.bclas.popcornpicks.presentation.view.fragments.DetailMovieBottomSheetDialogFragment
import br.com.bclas.popcornpicks.presentation.viewmodel.PopCornPicksListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity(), OnItemClickListener {

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
        setupStateFlowObserver()
    }

    private fun setupStateFlowObserver() {
        lifecycleScope.launch {
            homeViewModel.uiState("now_playing").collect {
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
        lifecycleScope.launch {
            homeViewModel.uiState("popular").collect {
                when (it) {
                    is UiState.Success -> {
                        binding.lblPopular.text = "Mais assistidos!"
                        fillListMoviesPopular(it.data)
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