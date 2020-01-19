package com.example.myproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowAllItems extends AppCompatActivity {
ListView ListView;
ArrayList<Item> itemlist;
customadapter customadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_items);
        ImageView back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView home=(ImageView)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ShowAllItems.this,MainActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                finish();
            }
        });

        ListView=findViewById(R.id.listview);

        //setting the title

        SQLiteDatabase data=openOrCreateDatabase("store",MODE_PRIVATE,null);
        data.execSQL("create table if not exists items (name varchar,brand varchar,quantity varchar,expiry varchar,mrp varchar,wsr varchar,serial varchar)");
        String s="select * from items";
        Cursor cursor=data.rawQuery(s,null);
        int i=0;
        int j=cursor.getCount();
        itemlist=new ArrayList<Item>();
        final String[] serial=new String[j];
        cursor.moveToFirst();
        while (i<j)
        {
            serial[i]=cursor.getString(cursor.getColumnIndex("serial"));
            itemlist.add(new Item("Product Name - "+cursor.getString(cursor.getColumnIndex("name")),"Brand - "+cursor.getString(cursor.getColumnIndex("brand"))));
            cursor.moveToNext();
            i++;
        }
        customadapter=new customadapter(this,R.layout.customlist,itemlist);
        ListView.setAdapter(customadapter);
        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ShowAllItems.this,showdetails.class);
                intent.putExtra("serial",serial[position]);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

}
