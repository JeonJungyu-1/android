package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {
    ScrollView scrollView;
    ImageView imageView;
    BitmapDrawable bitmap;
    ScrollView scrollView2;
    ImageView imageView2;
    BitmapDrawable bitmap2;
    int chk = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //image 1
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        imageView = (ImageView) findViewById(R.id.imageView);
        scrollView.setHorizontalScrollBarEnabled(true);

        Resources res = getResources();
        bitmap = (BitmapDrawable) res.getDrawable(R.drawable.image1);
        int bitmapWidth = bitmap.getIntrinsicWidth();
        int bitmapHeight = bitmap.getIntrinsicHeight();

        imageView.setImageDrawable(bitmap);
        imageView.getLayoutParams().width = bitmapWidth;
        imageView.getLayoutParams().height = bitmapHeight;

        //image 2
        scrollView2 = (ScrollView) findViewById(R.id.scrollView2);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        scrollView2.setHorizontalScrollBarEnabled(true);

        Resources res2 = getResources();
        bitmap2 = (BitmapDrawable) res2.getDrawable(R.drawable.image2);
        int bitmapWidth2 = bitmap2.getIntrinsicWidth();
        int bitmapHeight2 = bitmap2.getIntrinsicHeight();

        imageView2.setImageDrawable(bitmap2);
        imageView2.getLayoutParams().width = bitmapWidth2;
        imageView2.getLayoutParams().height = bitmapHeight2;
        }
        public void onButton1Clicked(View v) {
//            changeImage();
        }

        private void changeImage() {
            if (chk == 0) {
                imageView.setVisibility(View.VISIBLE);
                imageView2.setVisibility(View.INVISIBLE);
                chk = 1;
            } else if (chk == 1) {
                imageView.setVisibility(View.INVISIBLE);
                imageView2.setVisibility(View.VISIBLE);
                chk = 0;
            }
        }


}
