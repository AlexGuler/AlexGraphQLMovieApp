package com.podium.technicalchallenge.ui.genre

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.podium.technicalchallenge.databinding.ItemGenreBinding
import com.podium.technicalchallenge.util.toBinding

class BrowseByGenreAdapter(
    private val onGenreClick: (genre: String) -> Unit
) : ListAdapter<String, BrowseByGenreAdapter.BrowseByGenreViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrowseByGenreViewHolder =
        BrowseByGenreViewHolder(parent.toBinding(ItemGenreBinding::inflate))

    override fun onBindViewHolder(holder: BrowseByGenreViewHolder, position: Int) {
        getItem(position)?.let { genre ->
            holder.bind(genre, onGenreClick)
        }
    }

    class BrowseByGenreViewHolder(
        private val viewBinding: ItemGenreBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(genre: String, genreClick: (genre: String) -> Unit) {
            viewBinding.genreButton.text = genre
            viewBinding.genreButton.setOnClickListener {
                genreClick.invoke(genre)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
        }
    }
}
