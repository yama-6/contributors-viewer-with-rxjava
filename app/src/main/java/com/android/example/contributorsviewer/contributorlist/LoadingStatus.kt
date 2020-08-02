package com.android.example.contributorsviewer.contributorlist

enum class LoadingStatus {
    Initialized,
    Loading,
    Done,
    NetworkError,
    IOError,
    NoNetworkConnection
}

fun LoadingStatus.isError(): Boolean {
    return when(this) {
        LoadingStatus.NetworkError, LoadingStatus.IOError, LoadingStatus.NoNetworkConnection -> true
        else -> false
    }
}