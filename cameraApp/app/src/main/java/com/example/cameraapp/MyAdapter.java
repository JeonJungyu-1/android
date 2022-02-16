package com.example.cameraapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<PictureData> pictureData;

    public MyAdapter(Context context, ArrayList<PictureData> data) {
        mContext = context;
        pictureData = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return pictureData.size();
    }

    @Override
    public PictureData getItem(int position) {
        return pictureData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View listview = mLayoutInflater.inflate(R.layout.listview, null);

        TextView textView = (TextView) listview.findViewById(R.id.text);
        textView.setText(pictureData.get(position).getPictureName());
        return listview;
    }


}
