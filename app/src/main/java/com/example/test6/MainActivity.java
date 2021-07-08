package com.example.test6;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //creating variables
    EditText et1, et2, et3;
    Button b1, b2;
    LinearLayout ll1;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //creating objects
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        ll1 = findViewById(R.id.ll1);
        //connecting to database
        db = openOrCreateDatabase("Test6", MODE_PRIVATE, null);
        //executing a create table if not exists query
        db.execSQL("create table if not exists test(name text, pno text, email text)");
        //setting on click listener for save button
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating a insert query
                String query = "insert into test values('" + et1.getText().toString() + "', '" + et2.getText().toString() + "', '" + et3.getText().toString() + "')";
                //executing the query
                db.execSQL(query);
                //making a toast to inform user that data is saved
                Toast.makeText(MainActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
            }
        });
        //setting on click listener for show all button
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //removing all previous views
                ll1.removeAllViews();
                //creating a select query
                String query = "select * from test";
                //creating a cursor to get the result of executed query
                Cursor c = db.rawQuery(query, null);
                //checking if there is any record in cursor
                if(c.moveToFirst()) {
                    //doing while there are still records
                    do {
                        //creating a text view to show name
                        TextView tv1 = new TextView(MainActivity.this);
                        //setting name as text
                        tv1.setText(c.getString(0));
                        //setting text size
                        tv1.setTextSize(24);
                        //adding the text view to linear layout
                        ll1.addView(tv1);
                        //doing the same for text view two
                        TextView tv2 = new TextView(MainActivity.this);
                        tv2.setText(c.getString(1));
                        tv2.setTextSize(14);
                        ll1.addView(tv2);
                        //doing the same for text view three
                        TextView tv3 = new TextView(MainActivity.this);
                        tv3.setText(c.getString(2));
                        tv3.setTextSize(14);
                        ll1.addView(tv3);
                    } while(c.moveToNext());
                }
                else {
                    //creating toast to inform that there are no records
                    Toast.makeText(MainActivity.this, "No Record Available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}