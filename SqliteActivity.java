package com.example.sony.sagartaskdigital;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.jar.Attributes;

import static android.widget.Toast.LENGTH_LONG;

public class SqliteActivity extends AppCompatActivity {

    Controllerdb db =new Controllerdb(this);
    SQLiteDatabase database;
    Button Submitdatabtn,Showdatabtn;
    String name;
    String city;
    String coutry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        Submitdatabtn= (Button) findViewById(R.id.btnSave);
        Showdatabtn=(Button) findViewById(R.id.btnShow);


        // hide the default actionbar
        getSupportActionBar().hide();

        // Recieve data

         name  = getIntent().getExtras().getString("name");
         city = getIntent().getExtras().getString("city");
         coutry = getIntent().getExtras().getString("country");



        // ini views


        TextView names = findViewById(R.id.name);
        TextView citys = findViewById(R.id.city);
        TextView countrys = findViewById(R.id.country);



        // setting values to each view

        names.setText(name);
        citys.setText(city);
        countrys.setText(coutry);



        Submitdatabtn.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {

                database=db.getWritableDatabase();
                database.execSQL("INSERT INTO UserDetails(Username,Mailid,Age)VALUES('"+name+"','"+city+"','"+coutry+"')" );

                Toast.makeText(getBaseContext(), "data Inserted succusfully" , Toast.LENGTH_SHORT ).show();
            }

        });

        Showdatabtn.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {

                Intent i = new Intent(SqliteActivity.this, ShowdataListview.class);

// Starts TargetActivity
                startActivity(i);
            }

        });



    }







}
