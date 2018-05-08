package com.example.arsen.menu;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arsen.menu.Fragments.ExchangeFragment;

public class AddStorage extends AppCompatActivity {
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_storage);

        dbHelper = new DBHelper(this);
        setTitle(R.string.add_storage);
    }

    public void onAddFirstStorage(View view) {          //TODO WTFWTFWTFWTFWTF
        EditText storage_id = findViewById(R.id.FSid);
        String storage_name = storage_id.getText().toString();

        if (storage_name.equals("")){
            Toast.makeText(this, R.string.error_FS, Toast.LENGTH_LONG).show();
        } else {
            addStorage(storage_name);
        }
    }
//    public void onAddFirstStorage(View view){
//        EditText storage_id = findViewById(R.id.FSid);
//        String storage_name = storage_id.getText().toString();
//
//        if (storage_name.equals("")){
//            Toast.makeText(this, R.string.error_FS, Toast.LENGTH_LONG).show();
//        } else {
//            addStorage(storage_name);
//        }
//    }

    public void addStorage(String storage_name) {
        SQLiteDatabase storage = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.KEY_LOGIN, Global_variables.LOGIN);
        cv.put(DBHelper.KEY_STORAGE_NAME, storage_name);
        if (storage.insert(DBHelper.TABLE_STORAGES, null, cv) == -1) {
            Toast.makeText(this, R.string.message_existing_storage, Toast.LENGTH_SHORT).show();
        }
        storage.close();
//        if (getIntent().getBooleanExtra("From_exchange", false)) {    //TODO
//            ExchangeFragment fexchange = new ExchangeFragment();
//            getFragmentManager().beginTransaction().replace(R.id.container, fexchange).commit();
//        }
//        else {
            Intent intent = new Intent(this, MainPage.class);
            startActivity(intent);
//        }
    }
}
