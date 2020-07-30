package com.android.example.contributorsviewer

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.example.contributorsviewer.contributorlist.ContributorAdapter
import com.android.example.contributorsviewer.contributorlist.ContributorClickListener
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
//            .apply(
//                RequestOptions()
//// TODO add images
////                    .placeholder()
////                    .error()
//            )
        .into(this)
}