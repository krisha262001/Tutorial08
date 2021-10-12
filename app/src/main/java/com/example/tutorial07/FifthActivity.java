package com.example.tutorial07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class FifthActivity extends AppCompatActivity {

    TextView fname;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);

        getSupportActionBar().setTitle("Registration Detail");

        database = new Database(this);

        fname = findViewById(R.id.fname_set);
        String userAlldata = "";

        Intent intent = getIntent();
        String userdata = intent.getStringExtra("userdata");

        Cursor cursor = database.getSingleUserDetail(userdata);
        cursor.moveToFirst();

        userAlldata += "First Name: " + cursor.getString(1);
        userAlldata += "\nLast Name: " + cursor.getString(2);
        userAlldata += "\nUsername: " + cursor.getString(3);
        userAlldata += "\nPassword: " + cursor.getString(4);
        userAlldata += "\nGender: " + cursor.getString(6);
        userAlldata += "\nCity: " + cursor.getString(7);

        fname.setText(userAlldata);
    }
}