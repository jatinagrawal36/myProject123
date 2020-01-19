package com.example.myproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;
import java.util.Calendar;

import static android.app.PendingIntent.getActivity;

public class insertitems extends AppCompatActivity {
    EditText name,brand,quantity,expiry,mrp,wsr,serialno,dealername,company;
   Button b;
   String key,serial;

   //DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertitems);
       ImageView Backbutton=(ImageView)findViewById(R.id.back);
       ImageView home=(ImageView)findViewById(R.id.home);
       home.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent in = new Intent(insertitems.this,MainActivity.class);
               in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(in);
               finish();
           }
       });
        name=findViewById(R.id.editText2);
        brand=findViewById(R.id.editText3);
        quantity=findViewById(R.id.editText5);
        expiry=findViewById(R.id.editText6);

        mrp=findViewById(R.id.editText8);
        wsr=findViewById(R.id.editText9);
        serialno=findViewById(R.id.editText10);
        dealername=findViewById(R.id.dealername);
        company=findViewById(R.id.company);
        b=findViewById(R.id.button4);

        //get intent putextra
       final Intent intent=getIntent();
       key=intent.getStringExtra("key");
       serial=intent.getStringExtra("serial");
       // get data from database
        final SQLiteDatabase data = openOrCreateDatabase("store", MODE_PRIVATE, null);
        data.execSQL("create table if not exists items (name varchar,brand varchar,quantity varchar,expiry varchar,mrp varchar,wsr varchar,serial varchar)");
        data.execSQL("create table if not exists dealer (serial varchar,name varchar,company varchar)");
        String s = "select * from items where serial='" +serial+ "'";
        Cursor cursor=data.rawQuery(s,null);
        Cursor cursor1=data.rawQuery("select * from dealer where serial='" +serial+ "'",null);
        int i=0;
        cursor1.moveToFirst();
        cursor.moveToFirst();
        while (i<cursor.getCount())
        {
            String getname=cursor.getString(cursor.getColumnIndex("name"));
            String getbrand=cursor.getString(cursor.getColumnIndex("brand"));
            String getquantity=cursor.getString(cursor.getColumnIndex("quantity"));
            String getexpiry=cursor.getString(cursor.getColumnIndex("expiry"));
            String getmrp=cursor.getString(cursor.getColumnIndex("mrp"));
            String getwsr=cursor.getString(cursor.getColumnIndex("wsr"));
            String getdealer=cursor1.getString(cursor1.getColumnIndex("name"));
            String getcompany=cursor1.getString(cursor1.getColumnIndex("company"));
            name.setText(getname);
            brand.setText(getbrand);
            quantity.setText(getquantity);
            expiry.setText(getexpiry);
            mrp.setText(getmrp);
            wsr.setText(getwsr);
            serialno.setText(serial);
            serialno.setEnabled(false);
            expiry.setEnabled(false);
            dealername.setText(getdealer);
            company.setText(getcompany);
            cursor.moveToNext();
            cursor1.moveToNext();
            i++;
        }

       Backbutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });


       final DatePickerDialog[] picker = new DatePickerDialog[1];
      EditText eText;
       Button btnGet;
      TextView tvw;
      //tvw=(TextView)findViewById(R.id.textView1);
      //eText=(EditText) findViewById(R.id.editText1);


        expiry.setInputType(InputType.TYPE_NULL);
           expiry.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final Calendar cldr = Calendar.getInstance();
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);
                    // date picker dialog
                    picker[0] = new DatePickerDialog(insertitems.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    expiry.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                            }, year, month, day);
                    picker[0].show();
                }
            });


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String s2=name.getText().toString();
                String s3=brand.getText().toString();
                String s5=quantity.getText().toString();
                String s6=expiry.getText().toString();
                String s8=mrp.getText().toString();
                String s9=wsr.getText().toString();
                String s10=serialno.getText().toString();
                String gdealer=dealername.getText().toString();
                String gcompany=company.getText().toString();

                if(s2.equals("")||s3.equals("")||s5.equals("")||s6.equals("")||s8.equals("")||s9.equals("")||s10.equals("")||gdealer.equals("")||gcompany.equals(""))
                {
                    Toast.makeText(insertitems.this,"fill all",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    double mrp = Double.parseDouble(s8);
                    double wsr = Double.parseDouble(s9);
                    if(mrp<wsr)
                {
                    Toast.makeText(insertitems.this, "MRP SHOLD BE GRETER THAN WholeSale Rate", Toast.LENGTH_SHORT).show();
                }
                else
                    {
                      SQLiteDatabase data = openOrCreateDatabase("store", MODE_PRIVATE, null);
                      data.execSQL("create table if not exists items (name varchar ,brand varchar,quantity varchar,expiry varchar,mrp varchar,wsr varchar,serial varchar)");
                      if(key==null)
                      {
                      String s = "select * from items where serial='" + s10 + "'";
                      Cursor c = data.rawQuery(s, null);
                      if (c.getCount() > 0) {
                          Toast.makeText(insertitems.this, "Serial No. Exist", Toast.LENGTH_SHORT).show();
                      } else {

                              data.execSQL("Insert into items values('" + s2 + "','" + s3 + "','" + s5 + "','" + s6 + "','" + s8 + "','" + s9 + "','" + s10 + "')");
                              data.execSQL("Insert into dealer values('" + s10 + "','" + gdealer + "','" + gcompany + "')");
                              Toast.makeText(insertitems.this, "item inserted", Toast.LENGTH_SHORT).show();
                              Intent j = new Intent(insertitems.this, MainActivity.class);
                              startActivity(j);
                              finish();
                          }
                      }
                          else
                          {
                              data.execSQL("update items SET name='"+s2+"' where serial='"+serial+"'");
                              data.execSQL("update items SET brand='"+s3+"' where serial='"+serial+"'");
                              data.execSQL("update items SET quantity='"+s5+"' where serial='"+serial+"'");
                              data.execSQL("update items SET expiry='"+s6+"' where serial='"+serial+"'");
                              data.execSQL("update items SET mrp='"+s8+"' where serial='"+serial+"'");
                              data.execSQL("update items SET wsr='"+s9+"' where serial='"+serial+"'");
                              data.execSQL("update dealer SET name='"+gdealer+"' where serial='"+serial+"'");
                              data.execSQL("update dealer SET company='"+gcompany+"' where serial='"+serial+"'");
                              Intent intent1=new Intent(insertitems.this,showdetails.class);
                              intent1.putExtra("serial",serial);
                              startActivity(intent1);
                              finish();
                          }


                }
            }}
        });



      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        //setting the title
     //   toolbar.setTitle(" ");

        //placing toolbar in place of actionbar
       // setSupportActionBar(toolbar);

    }

  /*  @Override
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
