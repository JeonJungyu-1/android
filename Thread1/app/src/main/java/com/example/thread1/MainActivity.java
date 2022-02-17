package com.example.thread1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ToyThread thread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView.setText("Output");
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startBtn:
                thread = new ToyThread(handler, "hi");
                thread.setDaemon(true);
                thread.start();
                Toast.makeText(this, "Thread Start!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.stopBtn:
                if (thread != null) thread.interrupt();
                break;
            case R.id.toastBtn:
                Toast.makeText(this, "Toast!", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                textView.setText(textView.getText() + "\narg1: " + msg.arg1);
                textView.setText(textView.getText() + "\nObj: " + msg.obj);
            }
        }
    };

    class ToyThread extends Thread {
        final static String TAG = "ToyThread";

        String data;
        Handler handler;

        public ToyThread(Handler handler, String data) {
            this.data = data;
            this.handler = handler;
        }

        public void run() {
            Log.d(TAG, data + " thread start!");

            int sum = 0;
            for (int i = 1; i <= 100; i++) {
                sum += i;
                Log.d(TAG, "value: " + i);
                Log.d(TAG, "sum: " + sum);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }

            Message msg = handler.obtainMessage();

            msg.what = 1;
            msg.arg1 = sum;
            msg.obj = new Integer(sum);

            handler.sendMessage(msg);

            Log.d(TAG, data + " thread stop!");
        }
    }
}