package com.example.webviewprac;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle icicle) {
        webView = (WebView) findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebBrowserClient());
        webView.addJavascriptInterface(new JavaScriptMethods(), "sample");
        webView.loadUrl("file:///android_asset/www/sample.html");

        loadButton.setOnClickListener(new onClickListener() {
            public void onClick(View v) {
                webView.loadUrl(urlInput.getText().toString());
            }
        });

    }

    final class JavaScriptMethods {
        JavaScriptMethods() {

        }

        @android.webkit.JavascriptInterface
        public void clickOnFace() {
            hander.post(new Runnavle() {
                public void run() {
                    loadButton.setText("클릭 후 열기");
                    webView.loadUrl("javascript:changeFace()");
                }
            });
        }
    }

    final class WebBrowserClient extends WebChromeClient {
        public boolean onJsAlert(WebView view, String url, String message,
                                 JsResult result) {
            result.confirm();

            return true;
        }
    }
}