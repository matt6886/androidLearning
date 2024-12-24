package com.example.chaper08.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chaper08.fragment.LaunchFragment;

import java.util.ArrayList;

public class LaunchImprovementAdapter extends FragmentPagerAdapter {
    private final int[] mImageArray;

    public LaunchImprovementAdapter(@NonNull FragmentManager fm, int[] launchImageArray) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mImageArray = launchImageArray;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return LaunchFragment.newInstance(mImageArray.length, position, mImageArray[position]);
    }

    @Override
    public int getCount() {
        return mImageArray.length;
    }
}
