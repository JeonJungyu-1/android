package com.example.linearlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ThreeActivity extends AppCompatActivity {

    TextView clockTextView;
    private static Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        mHandler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Calendar cal = Calendar.getInstance();

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String strTime = sdf.format(cal.getTime());

                clockTextView = findViewById(R.id.clock);
                clockTextView.setText(strTime);
            }
        };

        class NewRunnable implements Runnable {
            @Override
            public void run() {
                while (true) {

                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    mHandler.post(runnable);
                }
            }
        }

        NewRunnable nr = new NewRunnable();
        Thread t = new Thread(nr);
        t.start();
    }
}