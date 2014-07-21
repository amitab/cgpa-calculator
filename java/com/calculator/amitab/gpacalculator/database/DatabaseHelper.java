package com.calculator.amitab.gpacalculator.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.calculator.amitab.gpacalculator.MainActivity;

/**
 * Created by amitab on 20/7/14.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cgpa.db";
    private static final Integer DATABASE_VERSION = 4;

    public static final String TABLE_SUBJECTS = "subjects";
    public static final String TABLE_USERS = "users";
    public static final String TABLE_USER_SUBJECT = "user_subject_score";
    public static final String TABLE_USER_SEMESTER = "user_semester_score";

    public static final String FIELD_USER_ID = "user_id";
    public static final String FIELD_USER_NAME = "user_name";

    public static final String FIELD_SUBJECT_CODE = "subject_code";
    public static final String FIELD_SUBJECT_NAME = "subject_name";
    public static final String FIELD_SUBJECT_CREDITS = "subject_credits";
    public static final String FIELD_SUBJECT_SEMESTER = "subject_semester";

    public static final String FIELD_USER_SUBJECT_SCORE = "user_subject_score";

    public static final String FIELD_SEMESTER = "semester";
    public static final String FIELD_USER_SCGPA = "semester_sgpa";
    public static final String FIELD_CREDITS_EARNED = "semester_credits";

    private static final String CREATE_TABLE_SUBJECTS = "CREATE TABLE " + TABLE_SUBJECTS + " ( "
            + FIELD_SUBJECT_CODE + " TEXT PRIMARY KEY, "
            + FIELD_SUBJECT_NAME + " TEXT, "
            + FIELD_SUBJECT_CREDITS + " INTEGER, "
            + FIELD_SUBJECT_SEMESTER + " INTEGER "
            + ")";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " ( "
            + FIELD_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FIELD_USER_NAME + " TEXT "
            + ")";

    private static final String CREATE_TABLE_USER_SUBJECTS = "CREATE TABLE " + TABLE_USER_SUBJECT + " ( "
            + FIELD_USER_ID + " INTEGER, "
            + FIELD_SUBJECT_CODE + " TEXT, "
            + FIELD_USER_SUBJECT_SCORE + " INTEGER DEFAULT 0 "
            + ")";

    private static final String CREATE_TABLE_USER_SEMESTER = "CREATE TABLE " + TABLE_USER_SEMESTER + " ( "
            + FIELD_USER_ID + " INTEGER, "
            + FIELD_SEMESTER + " INTEGER, "
            + FIELD_CREDITS_EARNED + " INTEGER, "
            + FIELD_USER_SCGPA + " REAL DEFAULT 0 "
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_SUBJECTS);
        sqLiteDatabase.execSQL(CREATE_TABLE_USERS);
        sqLiteDatabase.execSQL(CREATE_TABLE_USER_SUBJECTS);
        sqLiteDatabase.execSQL(CREATE_TABLE_USER_SEMESTER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_SUBJECT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_SEMESTER);

        onCreate(sqLiteDatabase);
    }
}
