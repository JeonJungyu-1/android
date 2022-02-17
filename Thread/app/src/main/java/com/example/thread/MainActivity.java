package com.example.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean loopFlag = true;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                textView.setText(String.valueOf(msg.arg1));
            } else if (msg.what == 2) {
                textView.setText((String)msg.obj);
            }
        }
    };




    class MyThread extends Thread {
        public void run() {
            try {
                int count = 10;
                while (loopFlag) {
                    Thread.sleep(1000);
                    if (isRun) {
                        count--;
                        Message message = new Message();
                        message.what = 1;
                        message.arg1 = count;
                        handler.sendMessage(message);
                        if (count == 0) {
                            message = new Message();
                            message.what = 2;
                            message.obj = "Finish!!";
                            handler.sendMessage(message);
                            loopFlag = false;
                        }
                    }
                }
            } catch (Exception e) {

            }
        }
    }
}