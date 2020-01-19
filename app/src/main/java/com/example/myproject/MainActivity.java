package com.example.myproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    //getSupportActionBar().setIcon(getDrawable(R.drawable.m));

ImageView imageView=(ImageView)findViewById(R.id.analysis);
imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(MainActivity.this,profit.class);
        startActivity(i);
    }
});
                Button clickis=(Button)findViewById(R.id.insert);
        clickis.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,insertitems.class);
                startActivity(i);
            }
        });



        Button clicksa=(Button)findViewById(R.id.showa);
        clicksa.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ShowAllItems.class);
                startActivity(i); 

            }
        });

        Button clickse=(Button)findViewById(R.id.showe);
        clickse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, showexpired.class);
                startActivity(i);

            }
        });

        Button clicko=(Button)findViewById(R.id.outofstock);
        clicko.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, outofstock.class);
                startActivity(i);



            }
        });
        Button clickb=(Button) findViewById(R.id.billview);
        clickb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(MainActivity.this,bill.class);
                startActivity(i);
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

