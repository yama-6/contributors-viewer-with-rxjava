package com.android.example.contributorsviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import com.android.example.contributorsviewer.data.api.GithubApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    var webView: WebView? = null

    fun clearWebView() {
        webView = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (webView == null)
            return super.onKeyDown(keyCode, event)
        if (keyCode != KeyEvent.KEYCODE_BACK || !webView!!.canGoBack())
            return super.onKeyDown(keyCode, event)

        webView!!.goBack()
        return true
    }
}
