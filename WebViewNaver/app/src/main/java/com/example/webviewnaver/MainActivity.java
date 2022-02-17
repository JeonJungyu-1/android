package com.example.webviewnaver;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button backBtn;
    Button goBtn;
    Button reBtn;
    Button homeBtn;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backBtn = findViewById(R.id.backBtn);
        goBtn = findViewById(R.id.goBtn);
        reBtn = findViewById(R.id.reBtn);
        homeBtn = findViewById(R.id.homeBtn);
        backBtn.setOnClickListener(this);
        goBtn.setOnClickListener(this);
        reBtn.setOnClickListener(this);
        homeBtn.setOnClickListener(this);

        webView = findViewById(R.id.webView);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.loadUrl("https://www.google.com");

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new MyWebClient());
    }




    @Override
    public void onClick(View v) {
        if (v == backBtn) {
            if (webView.canGoBack()) {
                webView.goBack();
            }
        } else if (v == goBtn) {
            if (webView.canGoForward()) {
                webView.goForward();
            }
        } else if (v == reBtn) {
            webView.reload();
        } else if (v == homeBtn) {
            webView.loadUrl("https://www.google.com");
        }
    }

    class MyWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (webView.canGoBack()) {
                backBtn.setEnabled(true);
            } else {
                backBtn.setEnabled(false);
            }
            if (webView.canGoForward()) {
                goBtn.setEnabled(true);
            } else {
                goBtn.setEnabled(false);
            }
        }
    }
}