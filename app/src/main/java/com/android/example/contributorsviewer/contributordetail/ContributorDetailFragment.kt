package com.android.example.contributorsviewer.contributordetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.example.contributorsviewer.databinding.ContributorDetailFragmentBinding

class ContributorDetailFragment : Fragment() {
    private val viewModel: ContributorDetailViewModel by lazy {
        val contributor =
            ContributorDetailFragmentArgs.fromBundle(requireArguments()).contributor
        ViewModelProvider(this, ContributorDetailViewModelFactory(contributor))
            .get(ContributorDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            ContributorDetailFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

}