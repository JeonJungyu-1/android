package com.example.cameraapp;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    ArrayList<PictureData> pictureDataList;
    private GridView m_grid;
    private GridAdapter m_gridAdt;
    ListView listView;
    Button btn1;
    Boolean buttonCheck = true;


    //https://3001ssw.tistory.com/199 참고
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView = (ListView) findViewById(R.id.listView);
                m_grid = (GridView) findViewById(R.id.grid_test);
                if (buttonCheck == true) {
                    printImageList(getImageList());

                    buttonCheck = false;
                } else {
                    m_gridAdt = new GridAdapter(getApplicationContext());

                    for (int i = 0; i < 100; i++) {
                        String strNo = "Num : " + i;
                        m_gridAdt.setItem(strNo);
                    }
                    m_grid.setAdapter(m_gridAdt);

                    listView.setVisibility(View.GONE);
                    m_grid.setVisibility(View.VISIBLE);

                    buttonCheck = true;
                }
            }
        });




    }


    //앱내 디렉토리 파일 가져오기
    public File[] getImageList() {
        File savedFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString());
        File[] savedFiles = savedFile.listFiles();
        return savedFiles;
    }


    //리스트 출력
    public void printImageList(File[] savedFiles) {
        pictureDataList = new ArrayList<>();
        for (File tempFile : savedFiles) {
            pictureDataList.add(new PictureData(tempFile.getName()));
        }


        final MyAdapter myAdapter = new MyAdapter(getApplicationContext(), pictureDataList);
        listView.setAdapter(myAdapter);

        listView.setVisibility(View.VISIBLE);
        m_grid.setVisibility(View.GONE);
    }
}