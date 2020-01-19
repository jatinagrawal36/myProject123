package com.example.myproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class profit extends AppCompatActivity {
    TextView profit,loss,total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit);
        ImageView backbutton=(ImageView)findViewById(R.id.back);
        ImageView home=(ImageView)findViewById(R.id.home);
        profit=findViewById(R.id.profit);
        loss=findViewById(R.id.loss);
        total=findViewById(R.id.totalsale);
        SQLiteDatabase data = openOrCreateDatabase("store", MODE_PRIVATE, null);
        data.execSQL("create table if not exists incom (profit varchar,loss varchar,total varchar)");
        Cursor cursor=data.rawQuery("select * from incom",null);
        if(cursor.getCount()==0)
        {
            profit.setText("PROFIT=0");
            loss.setText("LOSS=0");
            total.setText("SALE=0");

        }
        else
        {
            cursor.moveToLast();
            profit.setText("PROFIT="+cursor.getString(cursor.getColumnIndex("profit"))+"/-");
            loss.setText("LOSS="+cursor.getString(cursor.getColumnIndex("loss"))+"/-");
            total.setText("SALE="+cursor.getString(cursor.getColumnIndex("total"))+"/-");
        }
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(profit.this,MainActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                finish();
            }
        });
    }
}
