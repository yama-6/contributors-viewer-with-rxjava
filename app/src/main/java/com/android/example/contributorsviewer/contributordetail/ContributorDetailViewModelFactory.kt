package com.android.example.contributorsviewer.contributordetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.example.contributorsviewer.data.model.Contributor

class ContributorDetailViewModelFactory(val contributor: Contributor) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContributorDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContributorDetailViewModel(contributor) as T
        }
        throw IllegalArgumentException()
    }
}