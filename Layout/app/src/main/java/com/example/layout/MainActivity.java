package com.example.layout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn1;
    Button btn2;
    Button btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        Intent intent = new Intent(this, sub.class);

        btn1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int status = motionEvent.getAction();
                if (status == MotionEvent.ACTION_DOWN) {
                    btn1.setTextColor(Color.parseColor("#FFFFFF"));
                    btn1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.clickedbutton));
                } else if (status == MotionEvent.ACTION_UP) {
                    startActivity(intent);
                    btn1.setTextColor(Color.parseColor("#111111"));
                    btn1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.custombutton));
                }
                return false;
            }
        });


    }
}