package com.android.example.contributorsviewer.contributorlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.example.contributorsviewer.data.model.Contributor
import com.android.example.contributorsviewer.databinding.ContributorListItemBinding
import com.android.example.contributorsviewer.databinding.GetMoreContributorsItemBinding

class ContributorAdapter(
    private val contributorClickListener: ContributorClickListener,
    private val getMoreClickListener: GetMoreClickListener
) : ListAdapter<Item, RecyclerView.ViewHolder>(ItemDiffCallback()) {

    override fun getItemViewType(position: Int) = getItem(position).type

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Item.Type.CONTRIBUTOR.ordinal -> ContributorViewHolder.from(parent)
            Item.Type.GET_MORE.ordinal -> GetMoreHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is ContributorViewHolder -> {
                item as Item.ContributorItem
                holder.bind(item.contributor, contributorClickListener)
            }
            is GetMoreHolder -> {
                item as Item.GetMoreItem
                holder.bind(item.nextPage, getMoreClickListener)
            }
        }
    }

    fun submitList(contributors: List<Contributor>, nextPage: Int?) {
        val list: List<Item>? = when (contributors.isEmpty()) {
            true -> null
            false -> contributors.map { Item.ContributorItem(it) } + Item.GetMoreItem(nextPage)
        }
        super.submitList(list)
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

class GetMoreHolder private constructor(private val binding: GetMoreContributorsItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(nextPage: Int?, clickListener: GetMoreClickListener) {
        binding.nextPage = nextPage
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): GetMoreHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding =
                GetMoreContributorsItemBinding.inflate(layoutInflater, parent, false)
            return GetMoreHolder(binding)
        }
    }
}


sealed class ClickListener<T>(val callback: (item: T) -> Unit) {
    fun onClick(item: T) = callback(item)
}

class ContributorClickListener(callback: (contributor: Contributor) -> Unit) :
    ClickListener<Contributor>(callback)

class GetMoreClickListener(callback: (nextPage: Int) -> Unit) :
    ClickListener<Int>(callback)


class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldContributor: Item, newContributor: Item): Boolean {
        return oldContributor.id == newContributor.id
    }

    override fun areContentsTheSame(oldContributor: Item, newContributor: Item): Boolean {
        return oldContributor == newContributor
    }
}


sealed class Item {
    data class ContributorItem(val contributor: Contributor) : Item() {
        override val id = contributor.id
        override val type: Int = Type.CONTRIBUTOR.ordinal
    }

    class GetMoreItem(val nextPage: Int?) : Item() {
        override val id = Long.MIN_VALUE
        override val type: Int = Type.GET_MORE.ordinal
    }

    abstract val id: Long
    abstract val type: Int

    enum class Type { CONTRIBUTOR, GET_MORE }
}