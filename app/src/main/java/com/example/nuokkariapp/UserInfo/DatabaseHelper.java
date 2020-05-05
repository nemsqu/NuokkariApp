package com.example.nuokkariapp.UserInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper  {

    private static final String tag = "DatabaseHelper";


    private static final String TABLE_NAME = "users_table";
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

    //returns a cursor for obtaining information from database
    public Cursor getInformation(SQLiteDatabase db){
        String[] projections = {column1, column2, column3, column4, column5};
        Cursor cursor = db.query(TABLE_NAME, projections, null, null, null, null, null);
        return cursor;
    }

    //returns a cursor for obtaining user ID from database
    public Cursor getID(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT " + column1 + " FROM " + TABLE_NAME + " WHERE " + column2 + " = '" + email +"'";
        return db.rawQuery(query, null);
    }

    public void updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + column2 + " = '" + user.getEmail() + "', " + column3 + " = '" + user.getName() +
                "', " + column4 + " = '" + user.getPhoneNumber() + "', " + column5 + " = '" + user.getPassword() + "' WHERE " + column1 + " = " +
                user.getID();
        Log.d(tag, "UpdateName: query: " + query);
        db.execSQL(query);
    }
}
