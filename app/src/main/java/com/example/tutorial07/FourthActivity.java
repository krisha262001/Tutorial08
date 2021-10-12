package com.example.tutorial07;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FourthActivity extends AppCompatActivity {

    Database database;

    EditText fname,lname,username,password;
    Spinner city;
    CheckBox agree;
    Button Signup;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch branch;
    RadioGroup rdb_group;
    RadioButton rdb_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        getSupportActionBar().setTitle("Registration");
        database = new Database(this);

        fname = findViewById(R.id.edtFirstName);
        lname = findViewById(R.id.edtLastName);
        branch=findViewById(R.id.swtCe);
        username = findViewById(R.id.edtRUsername);
        password = findViewById(R.id.edtRPassword);
        city = findViewById(R.id.spiCity);
        agree = findViewById(R.id.chkStatus);

        Signup = findViewById(R.id.btnRegister);

        final Validate validation = new Validate();

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String first_name, last_name, uname, psw_str, br, gender, cy, ag;
                first_name = fname.getText().toString();
                last_name = lname.getText().toString();
                uname = username.getText().toString();
                psw_str = password.getText().toString();
                br = branch.getText().toString();
                rdb_group = findViewById(R.id.rbtGender);
                int id = rdb_group.getCheckedRadioButtonId();
                rdb_select = findViewById(id);
                gender = rdb_select.getText().toString();
                cy = city.getSelectedItem().toString();
                if (validation.isnull(fname.getText().toString())) {
                    fname.requestFocus();
                    fname.setError(("Please enter first name"));
                    return;
                }
                if (validation.isnull(lname.getText().toString())) {
                    lname.requestFocus();
                    lname.setError(("Please enter last name"));
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(uname).matches()) {
                    username.requestFocus();
                    username.setError(("Email address format is not valid"));
                    return;
                }
                if (database.duplicate_user(uname)) {
                    username.requestFocus();
                    username.setError(("Username already exist"));
                    return;
                }
                if (validation.isnull(password.getText().toString())) {
                    password.requestFocus();
                    password.setError(("Please Enter Password"));
                    return;
                }
                if (cy.equals("Select One")) {
                    Toast.makeText(FourthActivity.this, "Please Select City", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (agree.isChecked()) {
                    ag = "Active";
                } else {
                    ag = "Inactive";
                }
                boolean isInserted = database.insertData(first_name, last_name, uname, psw_str, br, gender, cy, ag);
                if (isInserted) {
                    Toast.makeText(FourthActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                    fname.setText("");
                    lname.setText("");
                    username.setText("");
                    password.setText("");
                    fname.requestFocus();
                    Intent intent = new Intent(FourthActivity.this, SecondActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(FourthActivity.this, "Something is wrong while inserting", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}