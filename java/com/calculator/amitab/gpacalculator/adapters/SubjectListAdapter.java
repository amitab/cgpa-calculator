package com.calculator.amitab.gpacalculator.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.calculator.amitab.gpacalculator.R;
import com.calculator.amitab.gpacalculator.models.Subject;

import java.util.List;

/**
 * Created by amitab on 20/7/14.
 */
public class SubjectListAdapter extends ArrayAdapter<Subject> {
    private Context context;
    public List<Subject> objects;

    static class ViewHolder {
        TextView subjectName;
        TextView subjectCode;
        TextView subjectGrade;
    }

    public SubjectListAdapter(Context context, int resource, List<Subject> objects) {
        super(context, resource, objects);

        this.context = context;
        this.objects = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Subject subject = objects.get(position);

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.subject_list_item, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.subjectName = (TextView) convertView.findViewById(R.id.subjectName);
            viewHolder.subjectCode = (TextView) convertView.findViewById(R.id.subjectCode);
            viewHolder.subjectGrade = (TextView) convertView.findViewById(R.id.subjectGrade);

            convertView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.subjectName.setText(subject.getSubjectName());
        viewHolder.subjectCode.setText(subject.getSubjectCode());
        viewHolder.subjectGrade.setText(subject.getGrade(subject.getScore()));

        return convertView;
    }

}
