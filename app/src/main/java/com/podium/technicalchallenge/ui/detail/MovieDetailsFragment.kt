package com.podium.technicalchallenge.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.podium.technicalchallenge.databinding.FragmentMovieDetailBinding
import com.podium.technicalchallenge.util.bindingLifecycle

class MovieDetailsFragment : Fragment() {

    private var binding: FragmentMovieDetailBinding by bindingLifecycle()
    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMovieDetailBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailTitle.text = "Title: ${args.movie.title}"
    }

    companion object {
        private const val TAG = "MovieDetailsFragment"
    }
}
