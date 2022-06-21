package com.podium.technicalchallenge.ui.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.podium.technicalchallenge.databinding.FragmentAssociatedMoviesBinding
import com.podium.technicalchallenge.entity.MovieEntity
import com.podium.technicalchallenge.ui.topfive.TopMoviesAdapter
import com.podium.technicalchallenge.ui.view.OnSortSelectedListener
import com.podium.technicalchallenge.util.bindingLifecycle

class AssociatedMoviesFragment : Fragment(), OnSortSelectedListener {

    private var viewBinding: FragmentAssociatedMoviesBinding by bindingLifecycle()
    private val viewModel: GenreViewModel by activityViewModels()
    private val args: AssociatedMoviesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAssociatedMoviesBinding.inflate(inflater, container, false).also {
        viewBinding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        viewModel.genre = args.genre
        viewModel.getMoviesByGenre(args.genre)
    }

    override fun onOrderBySelected(orderBy: String) {
        viewModel.onOrderByChanged(orderBy)
    }

    override fun onSortBySelected(sortBy: String) {
        viewModel.onSortByChanged(sortBy)
    }

    private fun onMovieClick(movieEntity: MovieEntity) {
        findNavController().navigate(
            AssociatedMoviesFragmentDirections.actionAssociatedMoviesFragmentToMovieDetailsFragment(
                movieEntity
            )
        )
    }

    private fun setupUi() {
        viewBinding.genreText.text = args.genre

        val moviesAdapter = TopMoviesAdapter(::onMovieClick)
        viewBinding.associatedMoviesRecyclerView.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
            adapter = moviesAdapter
        }
        viewBinding.associatedMoviesRetryButton.setOnClickListener {
            viewModel.getMoviesByGenre(args.genre)
        }
        viewBinding.genreSortingSpinners.setOnSortSelectedListener(this)

        viewModel.associatedMovies.observe(viewLifecycleOwner) {
            moviesAdapter.submitList(it)
        }
        viewModel.associatedLoading.observe(viewLifecycleOwner) {
            viewBinding.associatedMoviesProgress.isVisible = it && moviesAdapter.itemCount == 0
        }
        viewModel.associatedError.observe(viewLifecycleOwner) {
            viewBinding.associatedMoviesRetryButton.isVisible = it
        }
    }
}
