package com.example.chaper08.adapter;

import android.util.Log;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chaper08.bean.GoodsInfo;
import com.example.chaper08.fragment.DynamicFragment;

import java.util.ArrayList;

public class MobilePagerAdapter extends FragmentPagerAdapter {
    private final ArrayList<GoodsInfo> mGoodsList;

    public MobilePagerAdapter(@NonNull FragmentManager fm, ArrayList<GoodsInfo> goodsList) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mGoodsList = goodsList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        GoodsInfo info = mGoodsList.get(position);
        return DynamicFragment.newInstance(position, info.pic, info.description);
    }

    @Override
    public int getCount() {
        return mGoodsList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Log.d("Matt", mGoodsList.get(position).name);
        return mGoodsList.get(position).name;
    }
}
