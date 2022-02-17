package com.example.prac1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button webBtn;
    Button webBtn2;
    TextView textView;
    ActivityResultLauncher<Intent> startActivityResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        webBtn = findViewById(R.id.webBtn);
        webBtn.setOnClickListener(this);
        webBtn2 = findViewById(R.id.webBtn2);
        webBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                intent.putExtra("url", "https://dict.naver.com");
                startActivityResult.launch(intent);
            }
        });


        startActivityResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            String text = result.getData().getStringExtra("text");
                            textView.setText(text);
                        }
                    }
                }
        );



    }

    @Override
    public void onClick(View v) {
        if (v == webBtn) {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("url", "https://www.google.com");
//            startActivityForResult(intent, 10);
            startActivityResult.launch(intent);
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if ((requestCode == 10) && (resultCode == RESULT_OK)) {
//            String text = data.getStringExtra("text");
//            textView.setText(text);
//        }
//    }
}