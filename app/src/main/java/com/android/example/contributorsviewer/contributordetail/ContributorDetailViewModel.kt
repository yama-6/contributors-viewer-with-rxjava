package com.android.example.contributorsviewer.contributordetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.contributorsviewer.data.model.Contributor
import com.android.example.contributorsviewer.data.model.ContributorDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ContributorDetailViewModel(contributor: Contributor) : ViewModel() {
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _contributorDetail: MutableLiveData<ContributorDetail> = MutableLiveData()
    val contributorDetail: LiveData<ContributorDetail>
        get() = _contributorDetail

    init {
        getContributorDetailFromApi(contributor)
    }

    private fun getContributorDetailFromApi(contributor: Contributor) {

    }
}