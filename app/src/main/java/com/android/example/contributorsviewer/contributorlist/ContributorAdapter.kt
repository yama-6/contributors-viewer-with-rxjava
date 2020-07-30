package com.android.example.contributorsviewer.contributorlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.example.contributorsviewer.data.model.Contributor
import com.android.example.contributorsviewer.databinding.ContributorListItemBinding

class ContributorAdapter(private val clickListener: ContributorClickListener) :
    ListAdapter<Contributor, ContributorViewHolder>(ContributorDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorViewHolder =
        ContributorViewHolder.from(parent)

    override fun onBindViewHolder(holder: ContributorViewHolder, position: Int) {
        val contributor = getItem(position)
        holder.bind(contributor, clickListener)
    }
}

class ContributorViewHolder private constructor(private val binding: ContributorListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(contributor: Contributor, clickListener: ContributorClickListener) {
        binding.contributor = contributor
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ContributorViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding =
                ContributorListItemBinding.inflate(layoutInflater, parent, false)
            return ContributorViewHolder(binding)
        }
    }
}

class ContributorClickListener(val clickListener: (Contributor: Contributor) -> Unit) {
    fun onClick(contributor: Contributor) = clickListener(contributor)
}

class ContributorDiffCallback : DiffUtil.ItemCallback<Contributor>() {
    override fun areItemsTheSame(oldContributor: Contributor, newContributor: Contributor): Boolean {
        return oldContributor.id == newContributor.id
    }

    override fun areContentsTheSame(oldContributor: Contributor, newContributor: Contributor): Boolean {
        return oldContributor == newContributor
    }
}