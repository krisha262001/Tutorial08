package com.example.tutorial07;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    EditText Username;
    EditText Password;
    Button Login;
    Database database;
    String valUsername,valPassword;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        getSupportActionBar().setTitle("Login");

        database = new Database(this);
        Username = (EditText) findViewById(R.id.edtUsername);
        Password = (EditText) findViewById(R.id.edtPassword);
        Login=(Button) findViewById(R.id.Button);

        preferences=getSharedPreferences("user",MODE_PRIVATE);
        editor=preferences.edit();
        String userPreference=preferences.getString("username","");
        if(!userPreference.equals("")) {
            Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
            intent.putExtra("userdata_value", userPreference);
            startActivity(intent);
        }

        Login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                valUsername=Username.getText().toString();
                valPassword=Password.getText().toString();

                Log.i("Login Screen","In onClick");
                if(!Patterns.EMAIL_ADDRESS.matcher(valUsername).matches()) {
                    Toast.makeText(SecondActivity.this, "Email address format is not valid", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(database.validation(Username.getText().toString(),Password.getText().toString()))
                {
                    Toast.makeText(SecondActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                    Log.i("Login Screen","In onClick If");
                    editor.putString("username",valUsername);
                    editor.commit();
                    Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(SecondActivity.this, "Username or Password is incorrect.", Toast.LENGTH_SHORT).show();
                    Log.i("Login Screen","In onClick else");
                }
            }
        });
    }

    public void getRegister(View view) {
        Intent intent=new Intent(SecondActivity.this,FourthActivity.class);
        startActivity(intent);
    }
}