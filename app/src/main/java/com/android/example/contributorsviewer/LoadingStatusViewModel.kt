package com.android.example.contributorsviewer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class LoadingStatusViewModel : ViewModel() {
    protected val _loadingStatus: MutableLiveData<LoadingStatus> = MutableLiveData(LoadingStatus.Initialized)
    val loadingStatus: LiveData<LoadingStatus>
        get() = _loadingStatus

    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
    }
}