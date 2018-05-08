package com.example.arsen.menu;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationPage extends AppCompatActivity {
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        setTitle(R.string.registration);
        dbHelper = new DBHelper(this);
    }

    public void onRegister(View view){
        SQLiteDatabase log = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        EditText reg_login = findViewById(R.id.reg_login);
        EditText reg_password = findViewById(R.id.reg_password);
        EditText reg_rep_password = findViewById(R.id.reg_rep_password);
        EditText phone_number = findViewById(R.id.phone_number);

        Global_variables.LOGIN = reg_login.getText().toString();
        String password = reg_password.getText().toString();
        String rep_password = reg_rep_password.getText().toString();
        String phone = phone_number.getText().toString();

        if (Global_variables.LOGIN.equals("") || phone.equals("") || password.equals("") || rep_password.equals("")){
            Toast.makeText(this, R.string.error_empty, Toast.LENGTH_LONG).show();
        }else if (!password.equals(rep_password)){
            Toast.makeText(this, R.string.error_passwords, Toast.LENGTH_LONG).show();
            reg_password.setText("");
            reg_rep_password.setText("");
        }else if (Global_variables.LOGIN.length() < 5 || phone.length() < 11 || password.length() < 5){
            Toast.makeText(this, R.string.error_len, Toast.LENGTH_LONG).show();
        }else{
            cv.put(DBHelper.KEY_LOGIN, Global_variables.LOGIN);
            cv.put(DBHelper.KEY_PASSWORD, password);
            cv.put(DBHelper.KEY_PHONE, phone);
            log.insert(DBHelper.TABLE_USERS, null, cv);

            dbHelper.close();
            Intent ToAddingStorage = new Intent(this, AddStorage.class);
            startActivity(ToAddingStorage);
        }

    }
}
