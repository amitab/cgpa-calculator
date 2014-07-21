package com.calculator.amitab.gpacalculator.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by amitab on 20/7/14.
 */
public class DataSource {
    protected SQLiteOpenHelper dbHelper;
    protected SQLiteDatabase database;

    public DataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


}
