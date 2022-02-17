package com.example.linearlayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    WebView webView1;
    Button btn1;
    Button btn2;
    Button btn4;
    TextView textView1;

    ActivityResultLauncher<Intent> startActivityResult1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView1 = findViewById(R.id.webView1);
        WebSettings settings = webView1.getSettings();
        settings.setJavaScriptEnabled(true);
        webView1.setWebViewClient(new WebViewClient());
        webView1.setWebChromeClient(new WebChromeClient());

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView1.loadUrl("https://dict.naver.com");
            }
        });

        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OneActivity.class);
                intent.putExtra("text1", "mainActivity");
                startActivityResult1.launch(intent);
            }
        });
        textView1 = findViewById(R.id.textView1);
        startActivityResult1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null) {
                        String text1 = result.getData().getStringExtra("text1");
                        textView1.setText(text1);
                    } else {
                        textView1.setText("null");
                    }
                }
            }
        });

        btn4 = findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ThreeActivity.class);
                startActivity(intent);
            }
        });




    }
}