package com.calculator.amitab.gpacalculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.calculator.amitab.gpacalculator.adapters.UserListAdapter;
import com.calculator.amitab.gpacalculator.database.SemesterDataSource;
import com.calculator.amitab.gpacalculator.database.SubjectDataSource;
import com.calculator.amitab.gpacalculator.database.UserDataSource;
import com.calculator.amitab.gpacalculator.models.Semester;
import com.calculator.amitab.gpacalculator.models.Subject;
import com.calculator.amitab.gpacalculator.models.User;

import java.util.List;


public class MainActivity extends Activity {

    public ListView list;
    public TextView noUsersAlert;
    public ImageView sadFace;
    public LinearLayout linearLayout;

    public List<User> users;
    public UserListAdapter userListAdapter;

    public UserDataSource userDataSource;
    public SubjectDataSource subjectDataSource;

    public static User user;

    private SharedPreferences settings;
    private static final String FIRST_LAUNCH = "first_launch";

    public void initializeList() {
        list = (ListView) findViewById(R.id.userList);

        userDataSource = new UserDataSource(this);

        userDataSource.open();
        //userDataSource.createDummyUsers();
        //userDataSource.clearUsers();
        users = userDataSource.findAll();
        userDataSource.close();
        userListAdapter = new UserListAdapter(this, R.layout.user_list_item, users);

        list.setAdapter(userListAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                user = (User) list.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, SemesetersActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showInputDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Title");
        alert.setMessage("Enter Name : ");

        final EditText nameBox = new EditText(this);
        alert.setView(nameBox);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String userName = nameBox.getText().toString();
                userDataSource.open();
                User user = new User(userName);
                user.setCgpa(0d);
                user.setTotalCreditsEarned(0);
                user = userDataSource.createUser(user);

                users.add(user);

                if(noUsersAlert.getVisibility() != View.GONE) {
                    linearLayout.setGravity(Gravity.NO_GRAVITY);
                    linearLayout.removeView(noUsersAlert);
                    linearLayout.removeView(sadFace);
                }

                userListAdapter.notifyDataSetChanged();

                userDataSource.close();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = getPreferences(MODE_PRIVATE);
        linearLayout = (LinearLayout)findViewById(R.id.container);

        //setFirstLaunch(true);
        if(isFirstLaunch()) {
            setFirstLaunch(false);
            subjectDataSource = new SubjectDataSource(this);
            subjectDataSource.open();
            subjectDataSource.clearTable();
            subjectDataSource.createSubjects();
            subjectDataSource.close();
            Log.i("Testing", "First Launch");
        }

        initializeList();
        if(users.isEmpty()) {
            linearLayout.setGravity(Gravity.CENTER);
            noUsersAlert = new TextView(this);
            noUsersAlert.setText("Oh Noes! \nI could not find anyone!");
            noUsersAlert.setPadding(20, 20, 20, 20);
            noUsersAlert.setTextColor(Color.parseColor("#aaaaaa"));
            noUsersAlert.setTextSize(20);
            noUsersAlert.setGravity(Gravity.CENTER);

            sadFace = new ImageView(this);
            sadFace.setImageResource(R.drawable.sad);

            linearLayout.addView(sadFace);
            linearLayout.addView(noUsersAlert);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.action_add_user) {
            showInputDialog();
            return true;
        } else if(id == R.id.action_about) {
            Intent intent = new Intent(MainActivity.this, ShowOff.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isFirstLaunch() {
        return settings.getBoolean(FIRST_LAUNCH, true);
    }

    public void setFirstLaunch(boolean firstLaunch) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(FIRST_LAUNCH, firstLaunch);
        editor.commit();
    }

}
