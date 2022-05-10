package com.example.calmness

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment

class Horoscope_fragment : Fragment() {

    lateinit var browser: WebView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.horoscope_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        browser = view.findViewById(R.id.webview)
        browser.setWebViewClient(WebViewClient());
        browser.loadUrl("https://www.astrostar.ru/horoscopes/main/oven/day.html");
        //browser.loadUrl( arguments?.getString("URL")!!)

    }
}