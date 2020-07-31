package com.android.example.contributorsviewer

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.example.contributorsviewer.contributorlist.ContributorAdapter
import com.android.example.contributorsviewer.contributorlist.ContributorClickListener
import com.android.example.contributorsviewer.contributorlist.LoadingStatus
import com.android.example.contributorsviewer.data.model.Contributor
import com.bumptech.glide.Glide

@BindingAdapter("contributors")
fun RecyclerView.bindContributors(contributors: List<Contributor>?) {
    adapter?.let {
        val adapter = adapter as ContributorAdapter
        adapter.submitList(contributors)
    }
}

@BindingAdapter("onClickItemCallback")
fun RecyclerView.bindAdapter(callback: (contributor: Contributor) -> Unit) {
    val clickListener = ContributorClickListener(callback)
    adapter = ContributorAdapter(clickListener)
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