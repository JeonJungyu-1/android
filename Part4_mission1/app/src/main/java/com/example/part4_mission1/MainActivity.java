package com.example.part4_mission1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab10_2);
        ListView listView = findViewById(R.id.custom_listview);

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_calllog", null);

        ArrayList<DriveVO> datas = new ArrayList<DriveVO>();
        while (cursor.moveToNext()) {
            DriveVO vo = new DriveVO();
            vo.type = cursor.getString(2);
            vo.title = cursor.getString(1);
            vo.date = cursor.getString(3);
            vo.phone = cursor.getString(4);
            datas.add(vo);
        }
        db.close();

        DriveAdapter adapter = new DriveAdapter(this, R.layout.custom_item, datas);
        listView.setAdapter(adapter);
    }
}

