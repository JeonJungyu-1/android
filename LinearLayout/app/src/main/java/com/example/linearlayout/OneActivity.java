package com.example.linearlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OneActivity extends AppCompatActivity {

    TextView textView1;
    Button btn1;
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        textView1 = findViewById(R.id.textView1);

        Intent intent = getIntent();
        String text1 = intent.getStringExtra("text1");

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!text1.isEmpty()) {
                    textView1.setText(text1);
                }
            }
        });

        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.putExtra("text1", "oneactivity");
                setResult(RESULT_OK, intent);
                finish();
            }
        }));

    }
}