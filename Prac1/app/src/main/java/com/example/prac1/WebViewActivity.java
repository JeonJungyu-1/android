package com.example.prac1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {


    Button homeBtn;
    Button refreshBtn;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        homeBtn = findViewById(R.id.homeBtn);
        refreshBtn = findViewById(R.id.refreshBtn);
        homeBtn.setOnClickListener(this);
        refreshBtn.setOnClickListener(this);

        webView = findViewById(R.id.webView);

        Intent intent = getIntent();

        String url = intent.getStringExtra("url");

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
    }

    @Override
    public void onClick(View v) {
        String text = "";
        if (v == homeBtn) {
            text = "home";
        } else if (v == refreshBtn) {
            text = "refresh";
        }
        Intent intent = getIntent();
        intent.putExtra("text", text);
        setResult(RESULT_OK, intent);
        finish();
    }
}