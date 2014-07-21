package com.calculator.amitab.gpacalculator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.calculator.amitab.gpacalculator.models.Subject;
import com.calculator.amitab.gpacalculator.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amitab on 20/7/14.
 */
public class SubjectDataSource extends DataSource {

    private static final String[] allColumns = {
            DatabaseHelper.FIELD_SUBJECT_SEMESTER,
            DatabaseHelper.FIELD_SUBJECT_CREDITS,
            DatabaseHelper.FIELD_SUBJECT_NAME,
            DatabaseHelper.FIELD_SUBJECT_CODE
    };

    private static final String UPDATE_SCORE = DatabaseHelper.FIELD_SUBJECT_CODE
            + " = ? AND "
            + DatabaseHelper.FIELD_USER_ID + " = ?";

    public SubjectDataSource(Context context) {
        super(context);
    }

    public Subject createSubject(Subject subject) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, subject.getSubjectCode());
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, subject.getSubjectCredits());
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, subject.getSemester());
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, subject.getSubjectName());

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);

        return subject;
    }

    public long setUserSubjectScore(Subject subject, User user) {
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, subject.getSubjectCode());
        values.put(DatabaseHelper.FIELD_USER_ID, user.getUserId());
        values.put(DatabaseHelper.FIELD_USER_SUBJECT_SCORE, subject.getScore());

        long returnValue = database.insert(DatabaseHelper.TABLE_USER_SUBJECT, null, values);

        return returnValue;
    }

    public boolean updateUserScore(Subject subject, User user) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.FIELD_USER_SUBJECT_SCORE, subject.getScore());
        long returnValue = database.update(DatabaseHelper.TABLE_USER_SUBJECT,
                values,
                UPDATE_SCORE,
                new String[] { subject.getSubjectCode(), user.getUserId().toString() }
        );

        if(returnValue == 0) {
            returnValue = setUserSubjectScore(subject, user);
        }

        Log.i("Testing", "Updated Subject " + subject.getSubjectName() );

        return (returnValue > 0);
    }

    public Integer getSubjectScore(User user, Subject subject) {
        Cursor cursor = database.query(
                DatabaseHelper.TABLE_USER_SUBJECT,
                new String[] { DatabaseHelper.FIELD_USER_SUBJECT_SCORE },
                DatabaseHelper.FIELD_USER_ID + " = ? AND " + DatabaseHelper.FIELD_SUBJECT_CODE + " = ?",
                new String[] {
                        user.getUserId().toString(),
                        subject.getSubjectCode()
                },
                null, null, null
        );

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getInt(0);
        } else {
            return -99;
        }
    }

    public List<Subject> findSubjectsOfSemester(User user, Integer semester) {
        List<Subject> subjects = new ArrayList<Subject>();
        Subject subject;
        Cursor cursor = database.query(
                DatabaseHelper.TABLE_SUBJECTS,
                allColumns,
                DatabaseHelper.FIELD_SUBJECT_SEMESTER + " = ?",
                new String[] { semester.toString() },
                null, null, null
        );

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                subject = new Subject();
                subject.setSemester(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FIELD_SUBJECT_SEMESTER)));
                subject.setSubjectCode(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_SUBJECT_CODE)));
                subject.setSubjectCredits(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FIELD_SUBJECT_CREDITS)));
                subject.setSubjectName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_SUBJECT_NAME)));
                subject.setScore(getSubjectScore(user, subject));
                subject.clean();
                subjects.add(subject);
            }
        }

        cursor.close();

        return subjects;
    }

    public void clearTable() {
        database.delete(DatabaseHelper.TABLE_SUBJECTS, null, null);
    }

    public void createSubjects() {
        ContentValues values;

        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "11MA1ICMAT");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 1);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Engineering Math - I");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "11CY1ICCHY");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 5);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 1);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Engineering Chemistry");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "09EC1ICEEE");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 1);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Elements of Electronics Engineering");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "11CS1ICCCP");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 5);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 1);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Computer Concepts and C Programming");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "09ME1ICCED");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 1);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Computer Aided Engineering Drawing");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "08HS1ICEVS");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 2);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 1);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Environmental Studies");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "11HS1ICCIP");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 2);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 1);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Constituion of India and Professional Ethics");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "11MA2ICMAT");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 2);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Engineering Math - II");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "11PY2ICPHY");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 5);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 2);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Engineering Physics");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "11EE21CBEE");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 2);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Basic Electrical Engineering");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "08ME2ICEME");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 2);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Elements of Mechanical Engineering");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "09CV2ICENM");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 2);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Engineering Mechanics");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "09ME2ILWSP");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 1);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 2);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Workshop Practice");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "11HS2ICPDC");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 2);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 2);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Personality Development and Communication");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "11MA3ICMAT");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 3);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Engineering Math - III");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "09CI3GCMPL");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 6);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 3);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Microprocessors");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "09CI3GCLDL");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 5);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 3);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Logic Design");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "09CI3GCDSL");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 6);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 3);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Data Structures");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "09CI3GCCOA");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 3);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Computer Oragnization and Architecture");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "11MA4ICMAT");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Engineering Math - IV");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "09CI4GCTOF");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Theoretical Foundations of Computation");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "09CI4GCUNX");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 5);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Unix Programming");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "09CI4GCOOP");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 6);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Object Oriented Programming with C++");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "09CI4GCADA");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 6);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Analysis and Design of Algorithms");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "10CI5GCOPS");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 5);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Operating System");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "10CI5GCDCN");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 5);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Data Communications");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "10CI5GCDBM");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 6);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 5);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Database Management Systems");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "10CI5GCUSP");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 5);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 5);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Unix System Programming");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "10IS5DCJAV");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 6);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 5);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Java Programming");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "10CI6GCSWE");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 3);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 6);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Software Engineering");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "10CI6GCOOM");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 6);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Object Oriented Modeling and Design Patterns");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "10CI6GCPSQ");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 3);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 6);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Probability Statistics and Queuing");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "10CI6GCCON");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 6);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 6);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Computer Networks");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "10CI6GCWEP");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 6);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 6);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Web Programming");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "10CI6GEXXX");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 6);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Cluster Elective - I");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "11CI7LEXXX");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 6);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 7);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Special Cluster Elective and Lab");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "11CI7GEXXX");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 7);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Cluster Elective - II");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "11IS7DEXXX");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 7);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Elective - I (Department)");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "11CI7GCEAM");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 3);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 7);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Entrepreneurship and Management");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "11CI7IEXXX");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 7);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Institutional Elective - I");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "11CI7GCPPI");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 7);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Project Phase - I");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "11CI8EXXX");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 8);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Cluster Elective - III");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "11CI8GCICL");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 3);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 8);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Indian Cyber Law");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "11CI8IEXXX");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 4);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 8);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Institutional Elective - II");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);


        values = new ContentValues();
        values.put(DatabaseHelper.FIELD_SUBJECT_CODE, "11CI8GCPPT");
        values.put(DatabaseHelper.FIELD_SUBJECT_CREDITS, 13);
        values.put(DatabaseHelper.FIELD_SUBJECT_SEMESTER, 8);
        values.put(DatabaseHelper.FIELD_SUBJECT_NAME, "Project Phase - II");

        database.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);




    }

}
