package com.podium.technicalchallenge.ui.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.podium.technicalchallenge.databinding.ItemCastBinding
import com.podium.technicalchallenge.entity.Cast
import com.podium.technicalchallenge.util.toBinding

class CastAdapter : ListAdapter<Cast, CastAdapter.CastViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder =
        CastViewHolder(parent.toBinding(ItemCastBinding::inflate))

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }

    class CastViewHolder(
        private val viewBinding: ItemCastBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(cast: Cast) {
            Glide.with(viewBinding.root.context.applicationContext)
                .load(cast.imageUrl)
                .into(viewBinding.castImage)
            viewBinding.castName.text = cast.name
            viewBinding.castChar.text = cast.character
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cast>() {
            override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean =
                oldItem == newItem
            override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean =
                oldItem == newItem
        }
    }
}
