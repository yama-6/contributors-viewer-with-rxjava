package com.android.example.contributorsviewer

import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.example.contributorsviewer.contributorlist.*
import com.android.example.contributorsviewer.data.model.Contributor
import com.bumptech.glide.Glide

@BindingAdapter(
    "contributors", "nextPage", "onClickContributorCallback", "onClickGetMoreCallback"
)
fun RecyclerView.bind(
    contributors: List<Contributor>,
    nextPage: Int?,
    contributorCallback: (contributor: Contributor) -> Unit,
    getMoreCallback: (nextPage: Int) -> Unit
) {
    val contributorClickListener = ContributorClickListener(contributorCallback)
    val getMoreClickListener = GetMoreClickListener(getMoreCallback)
    adapter = ContributorAdapter(contributorClickListener, getMoreClickListener)
    (adapter as ContributorAdapter).submitList(contributors, nextPage)
}

@BindingAdapter("status")
fun RecyclerView.bindStatus(status: LoadingStatus) {
    alpha = when (status) {
        LoadingStatus.Loading -> 0.2F
        else -> 1.0F
    }
}

@BindingAdapter("imgUrl")
fun ImageView.bind(imgUrl: String) {
    val imgUri = Uri.parse(imgUrl)
    Glide.with(context)
        .load(imgUri)
        .into(this)
}

@BindingAdapter("status")
fun ProgressBar.bind(status: LoadingStatus) {
    visibility = when (status) {
        LoadingStatus.Loading -> View.VISIBLE

        // Other cases (NetworkError, NoNetworkConnection etc) are also handled by Observer in ContributorListFragment.
        else -> View.GONE
    }
}

@BindingAdapter("nextPage")
fun Button.bind(nextPage: Int?) {
    isEnabled = nextPage != null
}
