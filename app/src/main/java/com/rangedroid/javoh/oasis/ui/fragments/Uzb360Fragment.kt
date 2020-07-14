package com.rangedroid.javoh.oasis.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.ui.activities.HomeActivity
import java.lang.ref.WeakReference

@Suppress("DEPRECATION")
class Uzb360Fragment: Fragment(R.layout.uzb_360_fragment) {

    private lateinit var webView: WeakReference<WebView>

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = WeakReference(view.findViewById(R.id.webView))
        webView.get()?.settings?.javaScriptEnabled = true
        if ((activity as HomeActivity).unitProvider.isLocale()) {
            webView.get()?.loadUrl("https://uzbekistan360.uz/en")
        }else{
            webView.get()?.loadUrl("https://uzbekistan360.uz/ru")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.clear()
    }
}