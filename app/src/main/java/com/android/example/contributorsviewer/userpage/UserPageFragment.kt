package com.android.example.contributorsviewer.userpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.example.contributorsviewer.databinding.UserPageFragmentBinding

// User page means Github user page of the contributor.
class UserPageFragment : Fragment() {
    private val args: UserPageFragmentArgs by navArgs()

    private var webView: WebView? = null
        set(value) {
            when (lifecycle.currentState) {
                Lifecycle.State.CREATED -> field = value
                else -> throw IllegalStateException()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            UserPageFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        webView = binding.webview.apply {
            when (savedInstanceState) {
                null -> loadUrl(args.userPageUrl)
                else -> {
                    restoreState(savedInstanceState)
                    when (url) {
                        null -> loadUrl(args.userPageUrl)
                    }
                }
            }

            webViewClient = WebViewClient()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView!!.canGoBack()) {
                    webView!!.goBack()
                }
                else {
                    findNavController().popBackStack()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
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