package com.example.myproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.DialogInterface.*;

public class showdetails extends AppCompatActivity {
TextView name,brand,quantity,expdate,mrp,wholesel,serial,dealername,company;
Button back,update,delete;
String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdetails);
        delete = (Button) findViewById(R.id.delete);
        update=findViewById(R.id.update);
        dealername=findViewById(R.id.dealername);
        company=findViewById(R.id.company);

        ImageView back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(showdetails.this,MainActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                finish();
            }
        });
        ImageView home=(ImageView)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(showdetails.this,MainActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                finish();
            }
        });
    name=findViewById(R.id.textview);
    brand=findViewById(R.id.textview2);
    quantity=findViewById(R.id.textview3);
    expdate=findViewById(R.id.textview4);
    mrp=findViewById(R.id.textview5);
    wholesel=findViewById(R.id.textview6);
    serial=findViewById(R.id.textview7);
        final Intent intent=getIntent();
        value=intent.getStringExtra("serial");
        final SQLiteDatabase data = openOrCreateDatabase("store", MODE_PRIVATE, null);
        data.execSQL("create table if not exists items (name varchar,brand varchar,quantity varchar,expiry varchar,mrp varchar,wsr varchar,serial varchar)");
        data.execSQL("create table if not exists incom (profit varchar,loss varchar,total varchar)");
        data.execSQL("create table if not exists expiry (serial varchar)");
        data.execSQL("create table if not exists dealer (serial varchar,name varchar,company varchar)");
        String s = "select * from items where serial='" +value+ "'";
        final Cursor cursor=data.rawQuery(s,null);
        Cursor cursor1=data.rawQuery("select * from dealer where serial='" +value+ "'",null);

        int i=0;
        cursor.moveToFirst();
        cursor1.moveToFirst();
        while (i<cursor.getCount())
        {
            String s1="NAME = "+cursor.getString(cursor.getColumnIndex("name"));
            String s2="BRAND = "+cursor.getString(cursor.getColumnIndex("brand"));
            String s3="QUANTITY = "+cursor.getString(cursor.getColumnIndex("quantity"));
            String s4="EXPIRY DATE = "+cursor.getString(cursor.getColumnIndex("expiry"));
            String s5="MRP = "+cursor.getString(cursor.getColumnIndex("mrp"));
            String s6="WHOLESALE RATE = "+cursor.getString(cursor.getColumnIndex("wsr"));
            String s7="SERIAL NO. = "+cursor.getString(cursor.getColumnIndex("serial"));
            String s8="DEALER NAME="+cursor1.getString(cursor1.getColumnIndex("name"));
            String s9="COMPANY="+cursor1.getString(cursor1.getColumnIndex("company"));
            name.setText(s1);
            brand.setText(s2);
            quantity.setText(s3);
            expdate.setText(s4);
            mrp.setText(s5);
            wholesel.setText(s6);
            serial.setText(s7);
            dealername.setText(s8);
            company.setText(s9);
            cursor.moveToNext();
            cursor1.moveToNext();
            i++;
        }

        delete.setOnClickListener(new View.OnClickListener() {

                                      @Override
                                      public void onClick(View v) {
                                          //Creating the instance of PopupMenu
               PopupMenu popup = new PopupMenu(showdetails.this, delete);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popmenu, ((PopupMenu) popup).getMenu());

                //registering popup with OnMenuItemClickListener
                ((PopupMenu) popup).setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                 public boolean onMenuItemClick(MenuItem item) {
                        if((item.getTitle()).equals("Ok"))
                        {

                            Cursor cursor1=data.rawQuery("select * from expiry where serial='"+value+"'",null);
                            if(cursor.getCount()!=0)
                            {
                                Cursor cursor2=data.rawQuery("select * from items where serial='"+value+"'",null);
                                cursor2.moveToLast();
                                String getquantit=cursor2.getString(cursor2.getColumnIndex("quantity"));
                                String getwhole=cursor2.getString(cursor2.getColumnIndex("wsr"));
                                double q=Double.parseDouble(getquantit);
                                double w=Double.parseDouble(getwhole);
                                double loss=q*w;
                                String getloss=String.valueOf(loss);
                                String gloss=null;
                                Cursor cursor3=data.rawQuery("select * from incom",null);
                                if(cursor3.getCount()==0)
                                {
                                    String s="0";
                                    data.execSQL("Insert into incom values('"+s+"','"+getloss+"','"+s+"')");
                                }
                                else
                                {
                                    cursor3.moveToLast();
                                    gloss=cursor3.getString(cursor3.getColumnIndex("loss"));
                                    double dloss=Double.parseDouble(gloss);
                                    dloss=dloss+loss;
                                    String dgloss=String.valueOf(dloss);
                                    data.execSQL("update incom SET loss='"+dgloss+"' where loss='"+gloss+"'");
                                }
                                data.execSQL("delete from expiry where serial='" + value + "'");
                            }
                            data.execSQL("delete from items where serial='"+value+"'");
                            data.execSQL("delete from dealer where serial='"+value+"'");
                            Intent intent1=new Intent(showdetails.this,MainActivity.class);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent1);
                            finish();
                        }
                        return true;
                    }
                });

                popup.show();//showing popup menu

                                      }
                                  });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(showdetails.this,insertitems.class);

                intent1.putExtra("serial",value);
                intent1.putExtra("key","1");
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                finish();
            }
        });

    }


}
