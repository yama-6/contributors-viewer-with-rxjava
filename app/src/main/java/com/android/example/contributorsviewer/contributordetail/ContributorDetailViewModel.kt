package com.android.example.contributorsviewer.contributordetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.contributorsviewer.contributorlist.LoadingStatus
import com.android.example.contributorsviewer.data.api.GithubApi
import com.android.example.contributorsviewer.data.model.Contributor
import com.android.example.contributorsviewer.data.model.ContributorDetail
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

class ContributorDetailViewModel(contributor: Contributor) : ViewModel() {
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _contributorDetail: MutableLiveData<ContributorDetail> = MutableLiveData()
    val contributorDetail: LiveData<ContributorDetail>
        get() = _contributorDetail

    private val _loadingStatus: MutableLiveData<LoadingStatus> =
        MutableLiveData(LoadingStatus.Initialized)
    val loadingStatus: LiveData<LoadingStatus>
        get() = _loadingStatus

    init {
        getContributorDetailFromApi(contributor)
    }

    private fun getContributorDetailFromApi(contributor: Contributor) {
        _loadingStatus.value = LoadingStatus.Loading
        viewModelScope.launch {
            try {
                val contributorDetail = withContext(Dispatchers.IO) {
                    GithubApi.getContributorDetail(contributor.loginName).toContributorDetail(contributor)
                }

                _loadingStatus.value = LoadingStatus.Done
                _contributorDetail.value = contributorDetail
            }
            catch (e: Exception) {
                _loadingStatus.value = when (e) {
                    is HttpException -> LoadingStatus.NetworkError
                    is UnknownHostException -> LoadingStatus.NoNetworkConnection
                    is IOException -> LoadingStatus.IOError
                    else -> throw e
                }
            }
        }
    }

    override fun onCleared() {
        viewModelJob.cancel()
    }

}