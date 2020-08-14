package com.android.example.contributorsviewer

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.android.example.contributorsviewer.userpage.UserPageFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown(keyCode, event)
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val userPageFragment =
            navHostFragment.childFragmentManager.fragments.firstOrNull {
                it is UserPageFragment
            } as? UserPageFragment

        return when (true) {
            userPageFragment == null,
            ! userPageFragment.canWebViewGoBack
                -> super.onKeyDown(keyCode, event)

            else -> {
                userPageFragment.goBackOnWebView()
                true
            }
        }
    }
}
