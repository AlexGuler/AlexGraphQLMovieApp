package com.podium.technicalchallenge.ui.topfive

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.podium.technicalchallenge.databinding.ItemTopFiveMovieBinding
import com.podium.technicalchallenge.entity.MovieEntity
import com.podium.technicalchallenge.util.toBinding

class TopMoviesAdapter(
    private val onMovieClick: (MovieEntity) -> Unit
) : ListAdapter<MovieEntity, TopMoviesAdapter.TopFiveMovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopFiveMovieViewHolder =
        TopFiveMovieViewHolder(parent.toBinding(ItemTopFiveMovieBinding::inflate))

    override fun onBindViewHolder(holder: TopFiveMovieViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, onMovieClick)
        }
    }

    class TopFiveMovieViewHolder(
        private val viewBinding: ItemTopFiveMovieBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(movieEntity: MovieEntity, movieClick: (MovieEntity) -> Unit) {
            Glide.with(viewBinding.root.context.applicationContext)
                .load(movieEntity.imageUrl)
                .into(viewBinding.topFiveMovieImage)
            viewBinding.topFiveMovieTitle.text = movieEntity.title
            viewBinding.topFiveMovieRating.text = movieEntity.rating.toString()
            viewBinding.root.setOnClickListener {
                movieClick.invoke(movieEntity)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
                oldItem == newItem
        }
    }
}
