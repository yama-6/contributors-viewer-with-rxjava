package com.android.example.contributorsviewer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class LoadingStatusViewModel : ViewModel() {
    private val viewModelJob = Job()
    protected val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    protected val _loadingStatus: MutableLiveData<LoadingStatus> = MutableLiveData(LoadingStatus.Initialized)
    val loadingStatus: LiveData<LoadingStatus>
        get() = _loadingStatus

    override fun onCleared() {
        viewModelJob.cancel()
    }
}