package com.android.example.contributorsviewer.contributorlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.android.example.contributorsviewer.LoadingStatus
import com.android.example.contributorsviewer.R
import com.android.example.contributorsviewer.databinding.ContributorListFragmentBinding

class ContributorListFragment() : Fragment() {
    private val viewModel: ContributorListViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(ContributorListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            ContributorListFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.recyclerView.setContributorAdapter()

        observeLiveData()

        return binding.root
    }

    private fun RecyclerView.setContributorAdapter() {
        val contributorClickListener = ContributorClickListener { viewModel.onClickContributor(it) }
        val getMoreClickListener = GetMoreClickListener { viewModel.onClickGetMore(it) }
        adapter =
            ContributorAdapter(contributorClickListener, getMoreClickListener)

        (adapter as ContributorAdapter).registerAdapterDataObserver(
            object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    if (positionStart != 0) {
                        scrollToPosition(positionStart)
                    }
                }
            }
        )
    }

    private fun observeLiveData() {
        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                synchronized(Observer::class.java) {
                    if (findNavController().currentDestination!!.id != R.id.contributor_list) {
                        viewModel.doneNavigating()
                        return@Observer
                    }

                    val action =
                        ContributorListFragmentDirections
                            .actionContributorListToContributorDetail(it)
                    findNavController().navigate(action)
                    viewModel.doneNavigating()
                }
            }
        })

        viewModel.loadingStatus.observe(viewLifecycleOwner, Observer { status: LoadingStatus ->
            val toastMessageResId: Int = when (status) {
                LoadingStatus.NetworkError -> R.string.network_error
                LoadingStatus.NoNetworkConnection -> R.string.no_network_connection
                LoadingStatus.IOError -> R.string.io_error

                // Loading and Done cases are handled by Progressbar Binding adapter.
                else -> return@Observer
            }
            Toast.makeText(context, getString(toastMessageResId), Toast.LENGTH_LONG).show()
        })
    }

}
