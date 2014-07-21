package com.calculator.amitab.gpacalculator.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.calculator.amitab.gpacalculator.SubjectsFragment;

/**
 * Created by amitab on 21/7/14.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        Bundle bundle = new Bundle();
        SubjectsFragment subjectsFragment = new SubjectsFragment();

        bundle.putInt("semester", index + 1);

        subjectsFragment.setArguments(bundle);
        return subjectsFragment;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 8;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Semester " + (position + 1);
    }

}
