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

public class outofstock extends AppCompatActivity {
    ListView listView;
    customadapter customadapter;
    ArrayList<Item> itemlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outofstock);
        ImageView home=(ImageView)findViewById(R.id.home);
        itemlist=new ArrayList<Item>();
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(outofstock.this,MainActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                finish();
            }
        });
        ImageView back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView=findViewById(R.id.listview);
        SQLiteDatabase data=openOrCreateDatabase("store",MODE_PRIVATE,null);
        data.execSQL("create table if not exists items (name varchar,brand varchar,quantity varchar,expiry varchar,mrp varchar,wsr varchar,serial varchar)");
        String s="select * from items";
        Cursor cursor=data.rawQuery(s,null);
        int i=0;
        int k=0;
        int quantity;
        int j=cursor.getCount();
        final String[] serial=new String[j];
        cursor.moveToFirst();
        while (i<j)
        {
            quantity=Integer.parseInt(cursor.getString(cursor.getColumnIndex("quantity")));
            if(quantity<=0) {
                serial[k] = cursor.getString(cursor.getColumnIndex("serial"));
                itemlist.add(new Item("Product Name - " + cursor.getString(cursor.getColumnIndex("name")),"Brand - " + cursor.getString(cursor.getColumnIndex("brand"))));

                k++;
            }
            cursor.moveToNext();
            i++;
        }
        customadapter=new customadapter(this,R.layout.customlist,itemlist);
        listView.setAdapter(customadapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(outofstock.this,showdetails.class);
                intent.putExtra("serial",serial[position]);
                startActivity(intent);

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
