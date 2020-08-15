package com.android.example.contributorsviewer.userpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.android.example.contributorsviewer.databinding.UserPageFragmentBinding

// User page means Github user page of the contributor.
class UserPageFragment : Fragment() {
    private var webView: WebView? = null
        set(value) {
            when (lifecycle.currentState) {
                Lifecycle.State.CREATED -> field = value
                else -> throw IllegalStateException()
            }
        }

    val canWebViewGoBack: Boolean
        get() = webView?.canGoBack() ?: false

    fun goBackOnWebView() {
        webView!!.goBack()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            UserPageFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val userPageUrl = UserPageFragmentArgs
            .fromBundle(requireArguments()).userPageUrl

        webView = binding.webview.apply {
            when (savedInstanceState) {
                null -> loadUrl(userPageUrl)
                else -> {
                    restoreState(savedInstanceState)
                    when (url) {
                        null -> loadUrl(userPageUrl)
                    }
                }
            }

            webViewClient = WebViewClient()
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        webView!!.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        webView = null
    }

}