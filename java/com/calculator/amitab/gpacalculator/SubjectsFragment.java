package com.calculator.amitab.gpacalculator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.calculator.amitab.gpacalculator.adapters.SubjectListAdapter;
import com.calculator.amitab.gpacalculator.database.SemesterDataSource;
import com.calculator.amitab.gpacalculator.database.SubjectDataSource;
import com.calculator.amitab.gpacalculator.models.Semester;
import com.calculator.amitab.gpacalculator.models.Subject;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by amitab on 21/7/14.
 */
public class SubjectsFragment extends Fragment {

    private Semester semester;
    private SubjectDataSource subjectDataSource;
    private SemesterDataSource semesterDataSource;
    private List<Subject> subjects;

    private ListView list;
    private SubjectListAdapter subjectListAdapter;

    private Context context;
    private TextView sgpa;
    private TextView comment;

    //private Double SGPA;

    public void setSGPA(Double sgpa) {
        sgpa = Semester.round(sgpa, 2, BigDecimal.ROUND_HALF_UP);
        this.sgpa.setText(sgpa.toString());
        if(sgpa == 6) {
            comment.setText(R.string.comment_border_case);
        } else if(sgpa > 6) {
            comment.setText(R.string.comment_success);
        } else if(sgpa < 6) {
            comment.setText(R.string.comment_fail);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_subjects, container, false);
        list = (ListView) rootView.findViewById(R.id.subjects);
        setHasOptionsMenu(true);

        context = getActivity();
        semester = new Semester();
        semester.setSemester(getArguments().getInt("semester", 0));
        semester.setUserId(MainActivity.user.getUserId());
        subjectDataSource = new SubjectDataSource(context);
        semesterDataSource = new SemesterDataSource(context);

        sgpa = (TextView) rootView.findViewById(R.id.sgpa);
        comment = (TextView) rootView.findViewById(R.id.comment);

        initializeList();

        semesterDataSource.open();
        Double SGPA = semesterDataSource.getUserSGPA(semester);
        semesterDataSource.close();

        if(SGPA == null) {
            calculateSGPA();
        } else {
            semester.setSgpa(SGPA);
            setSGPA(semester.getSgpa());
        }

        return rootView;
    }

    public void showInputDialog(final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setTitle(subjects.get(position).getSubjectName());
        alert.setMessage("Enter Grade : ");


        final NumberPicker numberPicker = new NumberPicker(context);
        numberPicker.setMaxValue(Subject.GRADES.length-2);
        numberPicker.setMinValue(0);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setDisplayedValues(Subject.GRADES);

        alert.setView(numberPicker);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Integer value = Subject.SCORES[numberPicker.getValue()];
                subjects.get(position).setScore(value);
                subjectListAdapter.notifyDataSetChanged();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

    public void reloadScores() {
        List<Subject> subjects = subjectListAdapter.objects;
        subjectDataSource.open();
        for(Subject subject : subjects) {
            if(subject.isDirty()) {
                subject.setScore(subjectDataSource.getSubjectScore(MainActivity.user, subject));
                subject.clean();
            }
        }
        subjectDataSource.close();
        subjectListAdapter.notifyDataSetChanged();
    }

    public void updateScore(Subject subject) {
        if(subject.isDirty()) {
            subjectDataSource.updateUserScore(subject, MainActivity.user);
            subject.clean();
        }
    }

    public void calculateSGPA() {
        List<Subject> subjects = subjectListAdapter.objects;
        Double SGPA = 0.0;
        Integer totalCredits = 0;

        subjectDataSource.open();
        for(Subject subject : subjects) {
            if(subject.getScore() == -99) {
                return;
            }
            updateScore(subject);
            if(subject.getScore() != -1) {
                SGPA = SGPA + subject.getScore() * subject.getSubjectCredits();
                totalCredits = totalCredits + subject.getSubjectCredits();
            }
        }
        subjectDataSource.close();

        SGPA = SGPA/totalCredits;
        semester.setSgpa(SGPA);
        semester.setCreditsEarned(totalCredits);

        semesterDataSource.open();
        semesterDataSource.updateUserSGPA(semester);
        semesterDataSource.close();

        setSGPA(SGPA);
    }

    public void initializeList() {
        subjectDataSource = new SubjectDataSource(context);
        subjectDataSource.open();
        subjects = subjectDataSource.findSubjectsOfSemester(MainActivity.user, semester.getSemester());
        if(subjects.isEmpty()) {
            subjectDataSource.clearTable();
            subjectDataSource.createSubjects();
        }
        subjectDataSource.close();

        subjectListAdapter = new SubjectListAdapter(context, R.layout.subject_list_item, subjects);

        list.setAdapter(subjectListAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                showInputDialog(position);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.subject_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_calculate) {
            calculateSGPA();

            return true;
        } else if (id == R.id.action_reload) {
            reloadScores();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
