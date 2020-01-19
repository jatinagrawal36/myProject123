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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class bill extends AppCompatActivity {
    EditText serial;
    TextView billno,total;
    Button add,end;
    Double totalamount=0.00;
    Double totalwsr=0.00;
    SQLiteDatabase data;
    int getbillno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        billno=findViewById(R.id.billno);
        total=findViewById(R.id.totalamount);
        serial=findViewById(R.id.edittext3);
        add=findViewById(R.id.add);
        end=findViewById(R.id.end);

        ImageView backbutton=(ImageView)findViewById(R.id.back);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView home=(ImageView)findViewById(R.id.home);

        //bill table create or open
        data = openOrCreateDatabase("store", MODE_PRIVATE, null);
        data.execSQL("create table if not exists bill (billno varchar,totalamount varchar,totalwsr varchar)");
        data.execSQL("create table if not exists incom (profit varchar,loss varchar,total varchar)");
        //get bill no
        Cursor c=data.rawQuery("select * from bill",null);
        if(c.getCount()==0)
        {
            getbillno=1;
        }
        else
        {
            c.moveToLast();
            getbillno=Integer.parseInt(c.getString(c.getColumnIndex("billno")))+1;
        }
        billno.setText(String.valueOf(getbillno));

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(bill.this,MainActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                finish();
            }
        });



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.execSQL("create table if not exists items (name varchar,brand varchar,quantity varchar,expiry varchar,mrp varchar,wsr varchar,serial varchar)");
                data.execSQL("create table if not exists expiry (serial varchar)");
                String getserial=serial.getText().toString();
                if(getserial.equals(""))
                {
                    Toast.makeText(bill.this, "fill serial no", Toast.LENGTH_SHORT).show();
                }
                else {
                    Cursor gc = data.rawQuery("select * from expiry where serial='" + getserial + "'", null);
                    if (gc.getCount() == 0)
                    {
                        String s = "select * from items where serial='" + getserial + "'";
                    Cursor cursor = data.rawQuery(s, null);
                    if (cursor.getCount() > 0) {
                        String amount = null, wsr = null, quantity = null;
                        int i = 0;
                        cursor.moveToFirst();
                        while (i < cursor.getCount()) {
                            amount = cursor.getString(cursor.getColumnIndex("mrp"));
                            wsr = cursor.getString(cursor.getColumnIndex("wsr"));
                            quantity = cursor.getString(cursor.getColumnIndex("quantity"));
                            cursor.moveToNext();
                            i++;

                        }
                        int quan = Integer.parseInt(quantity);
                        if (quan > 0) {
                            quan = quan - 1;
                            String newquentity = String.valueOf(quan);
                            data.execSQL("update items SET quantity='" + newquentity + "' where serial='" + getserial + "'");

                            double mrp = Double.parseDouble(amount);
                            double getwsr = Double.parseDouble(wsr);
                            totalamount = totalamount + mrp;
                            totalwsr = totalwsr + getwsr;
                            String totalam = totalamount.toString();
                            total.setText(totalam);
                            serial.setText("");
                        } else {
                            Toast.makeText(bill.this, "product is out of stock", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(bill.this, "item not found", Toast.LENGTH_SHORT).show();
                    }
                }
                    else
                    {
                        Toast.makeText(bill.this, "item is expired", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getbill=billno.getText().toString();
                String gettotal=total.getText().toString();
                String getwsr=String.valueOf(totalwsr);
                data.execSQL("Insert into bill values('"+getbill+"','"+gettotal+"','"+getwsr+"')");
                total.setText("");
                serial.setText("");
                Cursor d=data.rawQuery("select * from bill",null);
                d.moveToLast();
                getbillno=Integer.parseInt(d.getString(d.getColumnIndex("billno")))+1;
                billno.setText(String.valueOf(getbillno));
                Cursor e=data.rawQuery("select * from incom",null);
                double profit=totalamount-totalwsr;
                String getprofit=String.valueOf(profit);
                if(e.getCount()==0)
                {
                    String s="0";
                    data.execSQL("Insert into incom values('"+getprofit+"','"+s+"','"+gettotal+"')");
                }
                else
                {
                    e.moveToLast();
                    String prof=e.getString(e.getColumnIndex("profit"));
                    String loss=e.getString(e.getColumnIndex("loss"));
                    String totala=e.getString(e.getColumnIndex("total"));
                    double dprofit=Double.parseDouble(prof);
                    double dtotal=Double.parseDouble(totala);
                    dprofit=dprofit+profit;
                    dtotal=dtotal+totalamount;
                    String sprofit=String.valueOf(dprofit);
                    String stotal=String.valueOf(dtotal);
                    data.execSQL("update incom SET profit='"+sprofit+"' where loss='"+loss+"'");
                    data.execSQL("update incom SET total='"+stotal+"' where loss='"+loss+"'");
                }
                totalamount=0.00;
                totalwsr=0.00;
                Intent in = new Intent(bill.this,MainActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                finish();
            }
        });





     /*   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setting the title
        toolbar.setTitle(" ");

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);*/

    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menuAbout:
                Toast.makeText(this, "You clicked about", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuSettings:
                Toast.makeText(this, "You clicked settings", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuLogout:
                Toast.makeText(this, "You clicked logout", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuSearch:
                Toast.makeText(this, "You clicked on Search", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }*/
}
