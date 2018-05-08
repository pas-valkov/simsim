package com.example.arsen.menu;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME        = "SimSim";

    public static final String TABLE_USERS          = "users";
    public static final String TABLE_STORAGES       = "storages";
    public static final String TABLE_GOODS_TYPE          = "goods";

    public static final String KEY_USERS_ID         = "_id";
    public static final String KEY_LOGIN            = "login";
    public static final String KEY_PASSWORD         = "password";
    public static final String KEY_PHONE            = "phone";

    public static final String KEY_STORAGE_NAME     = "storage";
    public static final String KEY_GOOD_ID          = "_id";
    public static final String KEY_GOOD_TYPE        = "type";
    public static final String KEY_GOOD_ARTICLE     = "article";
    public static final String KEY_GOOD_SIZE        = "size";
    public static final String KEY_GOOD_COLOR       = "color";
    public static final String KEY_GOOD_PRODUCER    = "producer";
    public static final String KEY_GOOD_PRODUCER_CODE    = "producer_code";


    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_USERS + "(" +
                KEY_USERS_ID + " integer primary key, " +
                KEY_LOGIN + " text," +
                KEY_PASSWORD + " text," +
                KEY_PHONE + " text" + ");");
        db.execSQL("create table " + TABLE_STORAGES + "(" +
//                KEY_ID_STORAGES + " integer primary key, " +
                KEY_LOGIN + " text," +
                KEY_STORAGE_NAME + " text primary key" + ");");
        db.execSQL("create table " + TABLE_GOODS_TYPE + "(" +
                KEY_GOOD_ID + " integer primary key, " +
                KEY_STORAGE_NAME + " text," +
                KEY_GOOD_TYPE + " text," +
                KEY_GOOD_ARTICLE + " text," +
                KEY_GOOD_SIZE + " text," +
                KEY_GOOD_COLOR + " text," +
                KEY_GOOD_PRODUCER + " text," +
                KEY_GOOD_PRODUCER_CODE + " text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_USERS);
        db.execSQL("drop table if exists " + TABLE_STORAGES);
        db.execSQL("drop table if exists " + TABLE_GOODS_TYPE);
        onCreate(db);
    }
//    public void Delete(SQLiteDatabase db) {
//        db.execSQL("drop table if exists " + TABLE_USERS);
//        db.execSQL("drop table if exists " + TABLE_STORAGES);
//    }

    public static void Remove_Current_Storage (Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(DBHelper.TABLE_STORAGES, DBHelper.KEY_STORAGE_NAME + "= ?", new String[]{Global_variables.CURRENT_STORAGE});
        database.delete(DBHelper.TABLE_GOODS_TYPE, DBHelper.KEY_STORAGE_NAME + "= ?", new String[]{Global_variables.CURRENT_STORAGE});

        database.close();
    }

    public static String Get_Current_Storage (Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String selection = DBHelper.KEY_LOGIN + " = ?";
        Cursor cursor = database.query(DBHelper.TABLE_STORAGES,
                new String[] {DBHelper.KEY_STORAGE_NAME}, selection, new String[] {Global_variables.LOGIN},
                null, null, null);
        String rez = context.getResources().getString(R.string.no_any_storage);
        if (cursor.moveToFirst()) {
            rez = cursor.getString(cursor.getColumnIndex(cursor.getColumnNames()[0]));
        }
        dbHelper.close();
        cursor.close();
        return rez;
    }

    public static void Get_Storages (ArrayList<String> storages, Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String selection = KEY_LOGIN + " = ?";
        Cursor cursor = database.query(DBHelper.TABLE_STORAGES, new String[]{KEY_STORAGE_NAME}, selection, new String[]{Global_variables.LOGIN}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                storages.add(name);

            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
    }

    public static void Fill_Goods_from_database(ArrayList<Good> goods, String table_name, String storage_name, Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String selection = KEY_STORAGE_NAME + " = ?";
        Cursor cursor = database.query(table_name, null, selection,
                new String[] {storage_name}, null, null, null);

        if (cursor.moveToFirst()) do {
            ArrayList<String> good_info = new ArrayList<>();
            for (String str : cursor.getColumnNames()) {
                good_info.add(cursor.getString(cursor.getColumnIndex(str)));
            }
            goods.add(new Good(good_info, cursor.getColumnCount()));
        } while (cursor.moveToNext());
        cursor.close();
        dbHelper.close();
    }

    public static int Get_database_size (Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String selection = DBHelper.KEY_LOGIN + " = ?";
        Cursor cursor = database.query(DBHelper.TABLE_STORAGES,
                null, selection, new String[] {Global_variables.LOGIN},
                null, null, null);
        int size = cursor.getCount();
        cursor.close();
        database.close();
        return size;
    }

    public static void print_user_database (Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase init = dbHelper.getReadableDatabase();
        Cursor c = init.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int idIndex = c.getColumnIndex(DBHelper.KEY_USERS_ID);
            int loginColInd = c.getColumnIndex(DBHelper.KEY_LOGIN);
            int passwordColInd = c.getColumnIndex(DBHelper.KEY_PASSWORD);
            do {
                Log.d("mLog", "ID = " + c.getInt(idIndex) +
                        ", LOGIN = " + c.getString(loginColInd) +
                        ", password = " + c.getString(passwordColInd));
            } while (c.moveToNext());
        } else
            Log.d("mLog", "EMPTY");
        c.close();
        dbHelper.close();
    }
    public static void print_storages_database (Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase init = dbHelper.getReadableDatabase();
        Cursor c = init.query(DBHelper.TABLE_STORAGES, null, null, null, null, null, null);
        if (c.moveToFirst()) {
//            int idIndex = c.getColumnIndex(DBHelper.KEY_ID_STORAGES);
            int loginColInd = c.getColumnIndex(DBHelper.KEY_LOGIN);
            int nameStColInd = c.getColumnIndex(DBHelper.KEY_STORAGE_NAME);
            do {
                Log.d("mLog",
//                        "ID = " + c.getInt(idIndex) +
                        "LOGIN = " + c.getString(loginColInd) +
                                ", storname = " + c.getString(nameStColInd));
            } while (c.moveToNext());
        } else
            Log.d("mLog", "EMPTY");
        c.close();
        dbHelper.close();
    }
    public static void print_goods_database (Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase init = dbHelper.getReadableDatabase();
        Cursor c = init.query(DBHelper.TABLE_GOODS_TYPE, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int idIndex = c.getColumnIndex(DBHelper.KEY_GOOD_ID);
            int StorageColInd = c.getColumnIndex(DBHelper.KEY_STORAGE_NAME);
            int ArticleColInd = c.getColumnIndex(DBHelper.KEY_GOOD_ARTICLE);
            int TypeColInd = c.getColumnIndex(DBHelper.KEY_GOOD_TYPE);
            int SizeColInd = c.getColumnIndex(DBHelper.KEY_GOOD_SIZE);
            int ColorColInd = c.getColumnIndex(DBHelper.KEY_GOOD_COLOR);
            int ProducerColInd = c.getColumnIndex(DBHelper.KEY_GOOD_PRODUCER);
            int ProducerCode = c.getColumnIndex(DBHelper.KEY_GOOD_PRODUCER_CODE);
            do {
                Log.d("mLog",
                        "ID = " + c.getInt(idIndex) +
                        "Storage = " + c.getString(StorageColInd) +
                                ", Article = " + c.getString(ArticleColInd) +
                                ", Type = " + c.getString(TypeColInd) +
                                ", Size = " + c.getString(SizeColInd) +
                                ", Color = " + c.getString(ColorColInd) +
                                ", Producer = " + c.getString(ProducerColInd) +
                                ", P_code = " + c.getString(ProducerCode));
            } while (c.moveToNext());
        } else
            Log.d("mLog", "EMPTY");
        c.close();
        dbHelper.close();
    }
}
