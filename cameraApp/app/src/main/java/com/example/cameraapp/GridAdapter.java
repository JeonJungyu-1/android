package com.example.cameraapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    private Context m_context;
    private ArrayList<GridItem> m_array_item;

    public GridAdapter(Context context) {
        this.m_context = context;
        this.m_array_item = new ArrayList<GridItem>();
    }

    @Override
    public int getCount() {
        return this.m_array_item.size();
    }

    @Override
    public Object getItem(int position) {
        return this.m_array_item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.grid_item, parent, false);

        TextView textView = convertView.findViewById(R.id.item_textview);
        textView.setText(m_array_item.get(position).getItemString());

        Button button = convertView.findViewById(R.id.item_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = getItemString(position);
                Toast.makeText(m_context, str, Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    public void setItem(String strItem) {
        String strGet = strItem;
        this.m_array_item.add(new GridItem(strGet));
    }

    public String getItemString(int position) {
        return this.m_array_item.get(position).getItemString();
    }
}
