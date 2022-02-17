package com.example.part2_misson1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button chkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chkBtn = findViewById(R.id.chkBtn);

        chkBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast t = Toast.makeText(this, "ok button click", Toast.LENGTH_SHORT);
        t.show();
    }
}