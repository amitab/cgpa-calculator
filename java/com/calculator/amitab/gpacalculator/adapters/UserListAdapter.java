package com.calculator.amitab.gpacalculator.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.calculator.amitab.gpacalculator.R;
import com.calculator.amitab.gpacalculator.models.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by amitab on 20/7/14.
 */
public class UserListAdapter extends ArrayAdapter<User> {

    private Context context;
    private List<User> objects;

    static class ViewHolder {
        TextView name;
        TextView userCGPA;
    }

    public UserListAdapter(Context context, int resource, List<User> objects) {
        super(context, resource, objects);

        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.user_list_item, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.userName);
            viewHolder.userCGPA = (TextView) convertView.findViewById(R.id.userCGPA);

            convertView.setTag(viewHolder);
        }

        User user = objects.get(position);
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.name.setText(user.userName);
        viewHolder.userCGPA.setText(user.getCgpa() + " / " + user.getTotalCreditsEarned() + " credits earned");

        return convertView;
    }

}
