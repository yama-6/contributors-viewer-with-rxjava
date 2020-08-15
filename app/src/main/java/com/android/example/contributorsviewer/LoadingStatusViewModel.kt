package com.android.example.contributorsviewer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class LoadingStatusViewModel : ViewModel() {
    protected val _loadingStatus: MutableLiveData<LoadingStatus> = MutableLiveData(LoadingStatus.Initialized)
    val loadingStatus: LiveData<LoadingStatus>
        get() = _loadingStatus
}