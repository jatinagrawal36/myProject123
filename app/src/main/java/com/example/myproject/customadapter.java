package com.example.myproject;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class customadapter extends ArrayAdapter<Item> {
    ArrayList<Item> itemlist;
    Activity context;

    public customadapter(@NonNull Context context, int resource, @NonNull ArrayList<Item> objects) {
        super(context, resource, objects);
        this.itemlist = objects;
        this.context = (Activity) context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View rowView=layoutInflater.inflate(R.layout.customlist,null,true);
        TextView nametext=rowView.findViewById(R.id.name);
        TextView brandtext=rowView.findViewById(R.id.brand);
        Item item=itemlist.get(position);
        nametext.setText(item.name);
        brandtext.setText(item.brand);
        return rowView;
    }
}
