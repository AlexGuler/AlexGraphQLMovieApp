package com.podium.technicalchallenge.ui.browsebyall

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
import com.podium.technicalchallenge.databinding.FragmentBrowseAllBinding
import com.podium.technicalchallenge.entity.MovieEntity
import com.podium.technicalchallenge.ui.genre.BrowseByGenreFragmentDirections
import com.podium.technicalchallenge.ui.topfive.TopMoviesAdapter
import com.podium.technicalchallenge.ui.topfive.TopMoviesFragmentDirections
import com.podium.technicalchallenge.ui.view.OnSortSelectedListener
import com.podium.technicalchallenge.util.bindingLifecycle

class BrowseByAllFragment : Fragment(), OnSortSelectedListener {

    private var viewBinding: FragmentBrowseAllBinding by bindingLifecycle()
    private val viewModel: BrowseByAllViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentBrowseAllBinding.inflate(inflater, container, false).also {
        viewBinding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        viewModel.getMovies()
    }

    override fun onOrderBySelected(orderBy: String) {
        viewModel.onOrderByChanged(orderBy)
    }

    override fun onSortBySelected(sortBy: String) {
        viewModel.onSortByChanged(sortBy)
    }

    private fun onMovieClick(movieEntity: MovieEntity) {
        findNavController().navigate(
            BrowseByAllFragmentDirections.actionBrowseByAllFragmentToMovieDetailsFragment(
                movieEntity
            )
        )
    }

    private fun setupUi() {
        val moviesAdapter = TopMoviesAdapter(::onMovieClick)
        viewBinding.allRecyclerVIew.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
            adapter = moviesAdapter
        }
        viewBinding.allRetryButton.setOnClickListener {
            // TODO: have viewModel have a retry() method instead of calling getMovies(). What if we need to retry getMovies(ASC, "title") ?
            viewModel.getMovies()
        }
        viewBinding.allSortingSpinners.setOnSortSelectedListener(this)

        viewModel.movies.observe(viewLifecycleOwner) {
            moviesAdapter.submitList(it)
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            viewBinding.allProgress.isVisible = it && moviesAdapter.itemCount == 0
        }
        viewModel.error.observe(viewLifecycleOwner) {
            viewBinding.allRetryButton.isVisible = it
        }
    }
}
