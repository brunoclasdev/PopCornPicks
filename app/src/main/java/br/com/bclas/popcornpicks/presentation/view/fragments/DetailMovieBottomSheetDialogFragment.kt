package br.com.bclas.popcornpicks.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.bclas.popcornpicks.R
import br.com.bclas.popcornpicks.databinding.FragmentDetailMovieBottomSheetDialogBinding
import br.com.bclas.popcornpicks.framework.util.BASE_URL_IMG
import br.com.bclas.popcornpicks.presentation.model.MovieModel
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailMovieBottomSheetDialogFragment : BottomSheetDialogFragment() {

    companion object {
        val newInstance: DetailMovieBottomSheetDialogFragment
            get() = DetailMovieBottomSheetDialogFragment()
    }

    private lateinit var binding: FragmentDetailMovieBottomSheetDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailMovieBottomSheetDialogBinding.inflate(inflater, container, false)
        val bundle = arguments?.getParcelable<MovieModel>("movie")
        bundle?.let {
            binding.lblTitleMovie.text = it.title
            binding.lblDescriptionTitleMovie.text = it.description
            binding.imageMovie.load(BASE_URL_IMG + it.posterPath) {
                crossfade(true)
                transformations(RoundedCornersTransformation(30f))
                size(300, 450)
            }
        }

        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialog
    }
}