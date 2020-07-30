package com.android.example.contributorsviewer.contributorlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.example.contributorsviewer.R
import com.android.example.contributorsviewer.databinding.ContributorListFragmentBinding
import kotlinx.android.synthetic.main.contributor_list_fragment.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import timber.log.Timber

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

        observeLiveData()

        return binding.root
    }

    private fun observeLiveData() {
        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                val action =
                    ContributorListFragmentDirections.actionContributorListToContributorDetail(it)
                findNavController().navigate(action)
                viewModel.doneNavigating()
            }
        })
    }

}
