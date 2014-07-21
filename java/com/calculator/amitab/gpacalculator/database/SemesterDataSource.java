package com.calculator.amitab.gpacalculator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.calculator.amitab.gpacalculator.models.Semester;
import com.calculator.amitab.gpacalculator.models.Subject;
import com.calculator.amitab.gpacalculator.models.User;

/**
 * Created by amitab on 20/7/14.
 */
public class SemesterDataSource extends DataSource {

    public SemesterDataSource(Context context) {
        super(context);
    }

    private static final String WHERE_SGPA_CLAUSE = DatabaseHelper.FIELD_USER_ID
            + " = ? AND "
            + DatabaseHelper.FIELD_SEMESTER + " = ?";

    public long setUserSGPA(Semester semester) {
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.FIELD_SEMESTER, semester.getSemester());
        values.put(DatabaseHelper.FIELD_USER_ID, semester.getUserId());
        values.put(DatabaseHelper.FIELD_USER_SCGPA, semester.getSgpa());
        values.put(DatabaseHelper.FIELD_CREDITS_EARNED, semester.getCreditsEarned());

        long returnValue = database.insert(DatabaseHelper.TABLE_USER_SEMESTER, null, values);
        Log.i("Testing", "Created SGPA for Semester " + semester.getSemester() );

        return returnValue;
    }

    public boolean updateUserSGPA(Semester semester) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.FIELD_USER_SCGPA, semester.getSgpa());
        values.put(DatabaseHelper.FIELD_CREDITS_EARNED, semester.getCreditsEarned());
        long returnValue = database.update(
                DatabaseHelper.TABLE_USER_SEMESTER,
                values,
                WHERE_SGPA_CLAUSE,
                new String[] { semester.getUserId().toString(), semester.getSemester().toString() }
        );

        if(returnValue == 0) {
            returnValue = setUserSGPA(semester);
        }

        Log.i("Testing", "Updated SGPA for Semester " + semester.getSemester() );

        return (returnValue > 0);
    }

    public Double getUserSGPA(Semester semester) {
        Cursor cursor = database.query(
                DatabaseHelper.TABLE_USER_SEMESTER,
                new String[] { DatabaseHelper.FIELD_USER_SCGPA },
                WHERE_SGPA_CLAUSE,
                new String[] { semester.getUserId().toString(), semester.getSemester().toString() },
                null, null, null
        );

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getDouble(0);
        } else {
            return null;
        }
    }

}
