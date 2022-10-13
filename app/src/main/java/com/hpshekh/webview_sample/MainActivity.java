package com.hpshekh.webview_sample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "hptag";
    private WebView webView;
    private EditText searchEdt;
    private TextView searchBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        searchBtn = findViewById(R.id.searchBtn);
        searchEdt = findViewById(R.id.searchEtd);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        // Enable responsive layout
        webSettings.setUseWideViewPort(true);
        // Zoom out if the content width is greater than the width of the viewport
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setSupportZoom(true);
        // Allow pinch to zoom
        webSettings.setBuiltInZoomControls(true);
        // Disable the default zoom controls on the page
        webSettings.setDisplayZoomControls(false);

        webView.setWebViewClient(new mWebViewClient());

        searchBtn.setOnClickListener(view -> {
            String url = searchEdt.getText().toString().trim();
            if (!url.isEmpty()) {
                GetWebsite(url);
            } else {
                Toast.makeText(this, "Please enter url", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataInWebView(String data) {
        webView.loadData("<html><body>Hello, Hakesh</body></html>", "text/html", "UTF-8");
    }

    private void GetWebsite(String url) {
        webView.loadUrl(url);
    }

    private class mWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            Log.e(TAG, "shouldOverrideUrlLoading: " + url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (!progressBar.isShown()) {
                progressBar.setVisibility(View.VISIBLE);
            }
            Log.e(TAG, "onPageStarted: " + url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (progressBar.isShown()) {
                progressBar.setVisibility(View.GONE);
            }
            searchEdt.setText(url);
            Log.e(TAG, "onPageFinished: " + url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            if (progressBar.isShown()) {
                progressBar.setVisibility(View.GONE);
            }
            Toast.makeText(MainActivity.this, "Unexpected error occurred.Reload page again.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            if (progressBar.isShown()) {
                progressBar.setVisibility(View.GONE);
            }
            Toast.makeText(MainActivity.this, "Unexpected error occurred.Reload page again.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            if (progressBar.isShown()) {
                progressBar.setVisibility(View.GONE);
            }
            Toast.makeText(MainActivity.this, "Unexpected SSL error occurred.Reload page again.", Toast.LENGTH_SHORT).show();
        }
    }

}