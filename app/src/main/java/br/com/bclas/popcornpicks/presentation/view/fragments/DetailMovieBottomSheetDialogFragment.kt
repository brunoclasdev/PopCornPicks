package br.com.bclas.popcornpicks.presentation.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import br.com.bclas.popcornpicks.R
import br.com.bclas.popcornpicks.databinding.FragmentDetailMovieBottomSheetDialogBinding
import br.com.bclas.popcornpicks.framework.util.BASE_URL_IMG
import br.com.bclas.popcornpicks.presentation.model.MovieModel
import br.com.bclas.popcornpicks.presentation.util.FormatDate
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailMovieBottomSheetDialogFragment : BottomSheetDialogFragment() {

    companion object {
        val newInstance: DetailMovieBottomSheetDialogFragment
            get() = DetailMovieBottomSheetDialogFragment()
    }

    private lateinit var binding: FragmentDetailMovieBottomSheetDialogBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailMovieBottomSheetDialogBinding.inflate(inflater, container, false)
        val bundle = arguments?.getParcelable<MovieModel>("movie")
        bundle?.let { movie ->
            binding.lblTitleMovie.text = movie.title
            binding.lblDescriptionTitleMovie.text = movie.description
            binding.imageMovie.doOnPreDraw {
                binding.imageMovie.load(BASE_URL_IMG + movie.backgroundImage) {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(topLeft = 80f, topRight = 80f))
                    size(binding.imageMovie.width, binding.imageMovie.height)
                }
            }
            binding.lblAverageVote.text = "Nota: " + movie.voteAverage.toString()
            val date = movie.releaseDate?.let { FormatDate.formateDate(it) }
            binding.lblReleaseDate.text = "Lançamento: ${date}"
        }

        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialog
    }
}