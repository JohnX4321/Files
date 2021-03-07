package com.thingsenz.files.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.thingsenz.files.R
import com.thingsenz.files.anim.AVLoadingIndicatorView


class AboutActivity : AppCompatActivity() {
    private var progressBar: AVLoadingIndicatorView? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = "About us"
        }
        val webView: WebView = findViewById<View>(R.id.webView) as WebView
        progressBar = findViewById<View>(R.id.progressBar) as AVLoadingIndicatorView
        webView.getSettings().setBuiltInZoomControls(false)
        webView.getSettings().setSupportZoom(false)
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true)
        webView.getSettings().setAllowFileAccess(true)
        webView.getSettings().setDomStorageEnabled(true)
        webView.canGoBack()
        webView.goBack()
        webView.loadUrl("http://www.androidhive.info/")
        progressBar!!.visibility = View.VISIBLE
        webView.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar!!.visibility = View.GONE
            }
        })
    }

    override fun onResume() {
        super.onResume()
        //FilerApp.getInstance().trackScreenView("About Screen")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}