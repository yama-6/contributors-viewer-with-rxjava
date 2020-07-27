package com.android.example.contributorsviewer

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.Default).launch {
            Timber.plant(Timber.DebugTree())
        }
    }
}