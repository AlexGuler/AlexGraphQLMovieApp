package com.podium.technicalchallenge.ui.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.podium.technicalchallenge.databinding.FragmentBrowseGenreBinding
import com.podium.technicalchallenge.util.bindingLifecycle

class BrowseByGenreFragment : Fragment() {

    private var viewBinding: FragmentBrowseGenreBinding by bindingLifecycle()
    private val viewModel: GenreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentBrowseGenreBinding.inflate(inflater, container, false).also {
        viewBinding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        viewModel.getGenres()
    }

    private fun onGenreClick(genre: String) {
        findNavController().navigate(
            BrowseByGenreFragmentDirections
                .actionBrowseByGenreFragmentToAssociatedMoviesFragment(genre)
        )
    }

    private fun setupUi() {
        val genreAdapter = BrowseByGenreAdapter(::onGenreClick)
        viewBinding.genreRecyclerView.adapter = genreAdapter
        viewBinding.genreRetryButton.setOnClickListener { viewModel.getGenres() }

        viewModel.genres.observe(viewLifecycleOwner) {
            genreAdapter.submitList(it)
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            viewBinding.genreProgress.isVisible = it && genreAdapter.itemCount == 0
        }
        viewModel.error.observe(viewLifecycleOwner) {
            viewBinding.genreRetryButton.isVisible = it
        }
    }
}
