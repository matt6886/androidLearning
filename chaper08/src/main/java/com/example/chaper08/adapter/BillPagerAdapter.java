package com.example.chaper08.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chaper08.bean.BillFragment;
import com.example.chaper08.bean.BillInfo;

import java.util.ArrayList;

public class BillPagerAdapter extends FragmentPagerAdapter {

    private final int year;

    public BillPagerAdapter(@NonNull FragmentManager fm, int year) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.year = year;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        int month = position + 1;
        String yearMonth = String.format("%d-%02d", year, month);
        Log.d("Matt", yearMonth);
        return BillFragment.newInstance(yearMonth);
    }

    @Override
    public int getCount() {
        return 12;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return (position + 1) + "月份";
    }
}
