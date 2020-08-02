package com.android.example.contributorsviewer.userpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.android.example.contributorsviewer.MainActivity
import com.android.example.contributorsviewer.databinding.UserPageFragmentBinding

class UserPageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            UserPageFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val userPageUrl = UserPageFragmentArgs
            .fromBundle(requireArguments()).userPageUrl

        binding.webview.apply {
            loadUrl(userPageUrl)
            webViewClient = WebViewClient()
            (requireActivity() as MainActivity).webView = this
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        (requireActivity() as MainActivity).clearWebView()
    }

}