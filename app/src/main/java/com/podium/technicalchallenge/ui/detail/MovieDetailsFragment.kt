package com.podium.technicalchallenge.ui.detail

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.databinding.FragmentMovieDetailBinding
import com.podium.technicalchallenge.util.bindingLifecycle
import com.podium.technicalchallenge.util.dipToPx

class MovieDetailsFragment : Fragment() {

    private var viewBinding: FragmentMovieDetailBinding by bindingLifecycle()
    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMovieDetailBinding.inflate(inflater, container, false).also {
        viewBinding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = args.movie
        Glide.with(requireContext().applicationContext)
            .load(movie.imageUrl)
            .into(viewBinding.detailImage)
        viewBinding.detailTitle.text = movie.title
        viewBinding.detailRating.text = getString(R.string.detail_rating, movie.rating)
        addGenresToFlexBox(movie.genres)
        viewBinding.detailDirector.text = getString(R.string.detail_director, movie.director.name)
        viewBinding.detailDescription.text = movie.overview
        val castAdapter = CastAdapter()
        viewBinding.detailCastRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = castAdapter
        }
        castAdapter.submitList(movie.cast)
    }

    private fun addGenresToFlexBox(genres: List<String>) {
        viewBinding.detailFlexBoxLayout.removeAllViews()
        genres.forEach {
            viewBinding.detailFlexBoxLayout.addView(
                TextView(requireContext()).apply {
                    setPadding(requireContext().dipToPx(3))
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.textColor))
                    text = it
                }
            )
        }
    }

    companion object {
        private const val TAG = "MovieDetailsFragment"
    }
}
