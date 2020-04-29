package com.example.nuokkariapp.UserInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper  {

    private static final String tag = "DatabaseHelper";

    private static final String TABLE_NAME = "users_table_test";
    private static final String column1 = "ID";
    private static final String column2 = "userEmail";
    private static final String column3 = "userName";
    private static final String column4 = "userPhone";
    private static final String column5 = "userPassword";


    DatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                column2 + " TEXT, " + column3 + " TEXT, " + column4 + " TEXT, " + column5 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    boolean addData(User newUser){
        System.out.println("addingUser");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(column2, newUser.getEmail());
        contentValues.put(column3, newUser.getName());
        contentValues.put(column4, newUser.getPhoneNumber());
        contentValues.put(column5, newUser.getPassword());

        Log.d(tag, "addData: Adding " + newUser + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }

    }
}
