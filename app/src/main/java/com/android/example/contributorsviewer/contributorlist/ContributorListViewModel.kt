package com.android.example.contributorsviewer.contributorlist

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.contributorsviewer.data.api.GithubApi
import com.android.example.contributorsviewer.data.api.dto.toContributorList
import com.android.example.contributorsviewer.data.model.Contributor
import kotlinx.coroutines.*
import okhttp3.Headers
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.lang.Exception
import java.net.UnknownHostException
import kotlin.IllegalStateException

enum class LoadingStatus { Initialized, Loading, Done, NetworkError,
    IOError, NoNetworkConnection }

class ContributorListViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _contributors: MutableLiveData<List<Contributor>> = MutableLiveData(emptyList())
    val contributors: LiveData<List<Contributor>>
        get() = _contributors

    private val _nextPage: MutableLiveData<Int?> = MutableLiveData()
    val nextPage: LiveData<Int?>
        get() = _nextPage

    private val _loadingStatus: MutableLiveData<LoadingStatus> =
        MutableLiveData(LoadingStatus.Initialized)
    val loadingStatus: LiveData<LoadingStatus>
        get() = _loadingStatus

    private val _navigateToDetail: MutableLiveData<String?> = MutableLiveData()
    val navigateToDetail: LiveData<String?>
        get() = _navigateToDetail

    init {
        getContributorsFromApi()
    }

    private fun getContributorsFromApi(page: Int = 1) {
        if (page < 1) throw IllegalArgumentException()

        _loadingStatus.value = LoadingStatus.Loading
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    GithubApi.getContributors(page)
                }
                if (!response.isSuccessful) throw HttpException(response)

                _loadingStatus.value = LoadingStatus.Done
                _nextPage.value = getNextPageValueFromHeaders(response.headers())

                _contributors.value = when (page > 1) {
                    false -> response.body()!!.toContributorList()
                    true -> _contributors.value!! + response.body()!!.toContributorList()
                }
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

    /*
    if next contributors pages exist, Header has Link field like:
        <https://api.github.com/repos/android/architecture-components-samples/contributors?page=2>; rel="next",
        <https://api.github.com/repos/android/architecture-components-samples/contributors?page=2>; rel="last"

    Max contributors count per page: 30
    */
    private fun getNextPageValueFromHeaders(headers: Headers): Int? {
        val link = headers["link"] ?: return null

        val splitted = link.split(',')
        splitted.forEach {
            val splitted_ = it.split(';')
            val last = splitted_.last()

            val regex = Regex("^ rel=\".*\"")
            val matched = regex.containsMatchIn(last)
            if (!matched) throw IllegalStateException()

            val relValue = last.substring(" rel=\"".length until last.length - 1)
            if (relValue == "next") {
                val first = splitted_.first()
                val urlStr = first.substring(1 until first.length - 1)
                val uri = Uri.parse(urlStr)
                val page = uri.getQueryParameter("page")?.toInt()
                    ?: throw IllegalStateException()

                return page
            }
        }

        return null
    }

    val onClickContributor = { contributor: Contributor ->
        if (_loadingStatus.value != LoadingStatus.Loading) {
            navigateToDetail(contributor.userPageUrl)
        }
    }

    val onClickGetMore = { nextPage: Int ->
        if (_loadingStatus.value != LoadingStatus.Loading) {
            getContributorsFromApi(nextPage)
        }
    }

    private fun navigateToDetail(userPageUrl: String) {
        _navigateToDetail.value = userPageUrl
    }

    fun doneNavigating() {
        _navigateToDetail.value = null
    }

    override fun onCleared() {
        viewModelJob.cancel()
    }

}