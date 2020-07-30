package com.android.example.contributorsviewer.contributorlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.contributorsviewer.data.api.GithubApi
import com.android.example.contributorsviewer.data.api.dto.toContributorList
import com.android.example.contributorsviewer.data.model.Contributor
import kotlinx.coroutines.*

class ContributorListViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _contributors: MutableLiveData<List<Contributor>?> = MutableLiveData(emptyList())
    val contributors: LiveData<List<Contributor>?>
        get() = _contributors

    init {
        refreshContributors()
    }

    fun refreshContributors() {
        viewModelScope.launch {
            _contributors.value = withContext(Dispatchers.IO) {
                GithubApi.getContributors().await().toContributorList()
            }
        }
    }

    val onClickContributor = { contributor: Contributor ->
        //TODO implement
    }

    override fun onCleared() {
        viewModelJob.cancel()
    }

}