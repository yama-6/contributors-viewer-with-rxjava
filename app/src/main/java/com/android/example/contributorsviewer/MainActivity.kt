package com.android.example.contributorsviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.example.contributorsviewer.api.GithubApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.IO).launch {
            val contributors = GithubApi.getContributors().await()

            Timber.d(contributors.size.toString())
            contributors.forEach {
                Timber.d("user name:%s", it.userName)
                Timber.d("avatar url:%s", it.avatarUrl)
            }
        }
    }
}
