package com.example.arsen.menu;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EnterPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_page);
    }

    boolean check(String eq1, String eq2){
        // проверка в бд соответствия Логина и пароля
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase init = dbHelper.getReadableDatabase();
        Cursor c = init.query(DBHelper.TABLE_USERS, null,null,null,null,null,null);

        if(c.moveToFirst()){
            int loginColInd = c.getColumnIndex(DBHelper.KEY_LOGIN);
            int passwordColInd = c.getColumnIndex(DBHelper.KEY_PASSWORD);

            do{
                if ((eq1.equals(c.getString(loginColInd))) && (eq2.equals(c.getString(passwordColInd))))
                    return true;
            } while(c.moveToNext());
            c.close();
            dbHelper.close();
            return false;
        }
        else{
            c.close();
            dbHelper.close();
            return false;
        }
    }

    public void onEnter(View view) {
        EditText enter_login = findViewById(R.id.enter_login);
        EditText enter_password = findViewById(R.id.enter_password);

        Global_variables.LOGIN = enter_login.getText().toString();
        String password = enter_password.getText().toString();

//        if (check(LOGIN, password)) { //TODO CHANGE
        if (true) {
            Intent ToMainPage = new Intent(this, MainPage.class);
            startActivity(ToMainPage);
        } else {
            Toast.makeText(this, R.string.error_enter, Toast.LENGTH_LONG).show();
            enter_login.setText(Global_variables.LOGIN);
            enter_password.setText("");
        }
    }

    public void onRegistration(View view){
        Intent ToRegistrationPage = new Intent(this, RegistrationPage.class);
        startActivity(ToRegistrationPage);
    }

}
