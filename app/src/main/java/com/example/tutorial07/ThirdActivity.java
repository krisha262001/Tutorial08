package com.example.tutorial07;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String prefName = "login";
    String prefKey =  "userkey";
    Database database;
    String [] data={"Dilip","Bhavesh"};
    ListView listView;
    ArrayAdapter<String> adapter;
    AlertDialog.Builder builder;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        getSupportActionBar().setTitle("Home");

        database=new Database(this);
        builder=new AlertDialog.Builder(this);
        listView=findViewById(R.id.lstview);

        adapter=new ArrayAdapter<String>(ThirdActivity.this,
                android.R.layout.simple_list_item_1,database.getUserList()
        );

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String listItem =((TextView)view).getText().toString();
                Intent intent=new Intent(ThirdActivity.this,FifthActivity.class);
                intent.putExtra("userdata",listItem);
                startActivity(intent);
            }
        });

        preferences=getSharedPreferences("user",MODE_PRIVATE);
        editor=preferences.edit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                builder.setTitle("Logging out")
                        .setMessage("Are you sure? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                editor.remove("username");
                                editor.commit();
                                Intent intent=new Intent(ThirdActivity.this,SecondActivity.class);
                                Toast.makeText(ThirdActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
