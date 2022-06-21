package com.podium.technicalchallenge.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import org.json.JSONObject

fun <T : ViewBinding> ViewGroup.toBinding(
    creator: (inflater: LayoutInflater, root: ViewGroup, attachToRoot: Boolean) -> T
): T = creator(
    LayoutInflater.from(context),
    this,
    false
)

fun String.asJSONQueryString() = JSONObject().apply {
    put("query", this@asJSONQueryString)
}.toString()
