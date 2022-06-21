package com.podium.technicalchallenge.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.ArrayRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.databinding.ViewSortingSpinnersBinding

class SortingSpinnersView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val viewBinding = ViewSortingSpinnersBinding.inflate(LayoutInflater.from(context), this)

    private var onSortSelectedListener: OnSortSelectedListener? = null

    init {
        setupAdapter(viewBinding.sortSpinner, R.array.sort_array)
        setupAdapter(viewBinding.orderBySpinner, R.array.order_by_array)
        viewBinding.sortSpinner.onItemSelectedListener = getOnItemSelectedListener { sortBy ->
            onSortSelectedListener?.onSortBySelected(sortBy)
        }
        viewBinding.orderBySpinner.onItemSelectedListener = getOnItemSelectedListener { orderBy ->
            onSortSelectedListener?.onOrderBySelected(orderBy)
        }
    }

    fun setOnSortSelectedListener(sortSelectedListener: OnSortSelectedListener) {
        onSortSelectedListener = sortSelectedListener
    }

    private fun setupAdapter(
        spinner: Spinner,
        @ArrayRes arrayRes: Int
    ) {
        ArrayAdapter.createFromResource(
            context,
            arrayRes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }

    private fun getOnItemSelectedListener(itemSelected: (String) -> Unit) =
        object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) = Unit
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                pos: Int,
                id: Long
            ) {
                parent?.getItemAtPosition(pos)?.let {
                    (it as? String)?.let(itemSelected)
                }
            }
        }
}

interface OnSortSelectedListener {
    fun onOrderBySelected(orderBy: String)
    fun onSortBySelected(sortBy: String)
}
