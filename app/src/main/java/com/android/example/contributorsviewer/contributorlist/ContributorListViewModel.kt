package com.android.example.contributorsviewer.contributorlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.contributorsviewer.data.api.GithubApi
import com.android.example.contributorsviewer.data.api.dto.toContributorList
import com.android.example.contributorsviewer.data.model.Contributor
import kotlinx.coroutines.*
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.lang.Exception
import java.net.UnknownHostException

enum class LoadingStatus { Initialized, Loading, Done, NetworkError,
    IOError, NoNetworkConnection }

class ContributorListViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _contributors: MutableLiveData<List<Contributor>?> = MutableLiveData(emptyList())
    val contributors: LiveData<List<Contributor>?>
        get() = _contributors

    private val _loadingStatus: MutableLiveData<LoadingStatus?> =
        MutableLiveData(LoadingStatus.Initialized)
    val loadingStatus: LiveData<LoadingStatus?>
        get() = _loadingStatus

    private val _navigateToDetail: MutableLiveData<String?> = MutableLiveData()
    val navigateToDetail: LiveData<String?>
        get() = _navigateToDetail

    init {
        refreshContributors()
    }

    fun refreshContributors() {
        _loadingStatus.value = LoadingStatus.Loading

        viewModelScope.launch {
            try {
                _contributors.value = withContext(Dispatchers.IO) {
                    GithubApi.getContributors().toContributorList()
                }
                _loadingStatus.value = LoadingStatus.Done
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

    val onClickContributor = { contributor: Contributor ->
        navigateToDetail(contributor.userPageUrl)
    }

    fun navigateToDetail(userPageUrl: String) {
        _navigateToDetail.value = userPageUrl
    }

    fun doneNavigating() {
        _navigateToDetail.value = null
    }

    override fun onCleared() {
        viewModelJob.cancel()
    }

}