package com.podium.technicalchallenge.ui.topfive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.podium.technicalchallenge.databinding.FragmentTopMoviesBinding
import com.podium.technicalchallenge.entity.MovieEntity
import com.podium.technicalchallenge.util.bindingLifecycle
import dagger.hilt.android.AndroidEntryPoint

// TODO: save recyclerview's scroll state
@AndroidEntryPoint
class TopMoviesFragment : Fragment() {

    private val viewModel: TopMoviesViewModel by activityViewModels()
    private var viewBinding: FragmentTopMoviesBinding by bindingLifecycle()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentTopMoviesBinding.inflate(inflater, container, false).also {
        viewBinding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        viewModel.getTopFiveMovies()
    }

    private fun onMovieClick(movieEntity: MovieEntity) {
        findNavController().navigate(
            TopMoviesFragmentDirections.actionNavigationHomeToMovieDetailsFragment(movieEntity)
        )
    }

    private fun setupUi() {
        val topFiveMoviesAdapter = TopMoviesAdapter(::onMovieClick)
        viewBinding.topMoviesRecyclerVIew.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
            adapter = topFiveMoviesAdapter
        }
        viewBinding.topMoviesRetryButton.setOnClickListener { viewModel.getTopFiveMovies() }

        viewModel.topMovies.observe(viewLifecycleOwner) {
            topFiveMoviesAdapter.submitList(it)
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            viewBinding.topMoviesProgress.isVisible = it && topFiveMoviesAdapter.itemCount == 0
        }
        viewModel.error.observe(viewLifecycleOwner) {
            viewBinding.topMoviesRetryButton.isVisible = it
        }
    }

    companion object {
        private const val TAG = "TopMoviesFragment"
    }
}
