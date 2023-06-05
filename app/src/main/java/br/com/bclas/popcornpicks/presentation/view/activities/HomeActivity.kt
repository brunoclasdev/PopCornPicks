package br.com.bclas.popcornpicks.presentation.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.bclas.popcornpicks.databinding.ActivityHomeBinding
import br.com.bclas.popcornpicks.presentation.util.ext.getScope
import org.koin.core.scope.Scope
import br.com.bclas.popcornpicks.framework.di.NAMED_MOVIE_hOME
import br.com.bclas.popcornpicks.presentation.state.UiState
import br.com.bclas.popcornpicks.presentation.util.ext.byViewModel
import br.com.bclas.popcornpicks.presentation.viewmodel.PopCornPicksListViewModel
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private val binding : ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val scope : Scope by lazy {
        getScope(NAMED_MOVIE_hOME)
    }

    private val homeViewModel : PopCornPicksListViewModel by scope.byViewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.lifecycleOwner = this
        binding.viewModel = homeViewModel

    }

    private fun setupStateFlowObserver(){
        lifecycleScope.launch{
            homeViewModel.uiState.collect{
                when(it){
                    is UiState.Success -> {
                        //TODO
                    }
                    is UiState.Loading -> {
                        //TODO
                    }
                    is UiState.Error -> {
                        //TODO
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        scope.close()
    }
}