package com.calculator.amitab.gpacalculator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.calculator.amitab.gpacalculator.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amitab on 20/7/14.
 */
public class UserDataSource extends DataSource {

    private static final String[] allColumns = {
            DatabaseHelper.FIELD_USER_ID,
            DatabaseHelper.FIELD_USER_NAME
    };

    private static final String FIND_USER_STATS_QUERY = "SELECT SUM(" + DatabaseHelper.FIELD_CREDITS_EARNED + "), "
            + "SUM(" + DatabaseHelper.FIELD_CREDITS_EARNED + "*" + DatabaseHelper.FIELD_USER_SCGPA + ") FROM " + DatabaseHelper.TABLE_USER_SEMESTER + " WHERE "
            + DatabaseHelper.FIELD_USER_ID + " = ?";

    public UserDataSource(Context context) {
        super(context);
    }

    public User createUser(User user) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.FIELD_USER_NAME, user.getUserName());

        Long userId = database.insert(DatabaseHelper.TABLE_USERS, null, values);
        user.setUserId(userId);
        return user;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<User>();
        User user;
        Cursor cursor = database.query(
                DatabaseHelper.TABLE_USERS,
                allColumns,
                null, null, null, null, null
        );

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                user = new User();
                user.setUserId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.FIELD_USER_ID)));
                user.setUserName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_USER_NAME)));
                user = getUserStats(user);
                users.add(user);
            }
        }

        cursor.close();

        return users;
    }

    public User getUserStats(User user) {

        Cursor cursor = database.rawQuery(
                FIND_USER_STATS_QUERY,
                new String[] { user.getUserId().toString() }
        );

        cursor.moveToFirst();
        Double cgpa = cursor.getDouble(1);
        Integer totalCredits = cursor.getInt(0);

        if(cgpa > 0) {

            cgpa = cgpa/totalCredits;
            user.setCgpa(cgpa);
            user.setTotalCreditsEarned(totalCredits);

        } else {
            user.setCgpa(0d);
            user.setTotalCreditsEarned(0);
        }

        return user;
    }

    public void clearScores() {
        database.delete(DatabaseHelper.TABLE_USER_SUBJECT, null, null);
    }
    public void clearUsers() {
        database.delete(DatabaseHelper.TABLE_USERS, null, null);
    }

    public void createDummyUsers() {
        createUser(new User("Amitabh Das"));
        createUser(new User("Joffery Lannister"));
        createUser(new User("Jamie Lannister"));
        createUser(new User("Robert Baratheon"));
        createUser(new User("Theon Greyjoy"));
        createUser(new User("Sansa Stark"));
        createUser(new User("Tyrion Lannister"));
    }

}
