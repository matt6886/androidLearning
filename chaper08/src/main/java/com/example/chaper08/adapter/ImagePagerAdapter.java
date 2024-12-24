package com.example.chaper08.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.example.chaper08.bean.GoodsInfo;

import java.util.ArrayList;

public class ImagePagerAdapter extends PagerAdapter {
    private Context mContent;
    private ArrayList<GoodsInfo> goodsList;

    private ArrayList<ImageView> imageList = new ArrayList<>();

    public ImagePagerAdapter(Context mContent, ArrayList<GoodsInfo> goodsList) {
        this.mContent = mContent;
        this.goodsList = goodsList;
        for (GoodsInfo goodsInfo : goodsList) {
            ImageView iv = new ImageView(mContent);
            iv.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            iv.setImageResource(goodsInfo.pic);
            imageList.add(iv);
        }
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // 添加一个view到container中，然后返回一个跟这个view可以关联起来的view
        // 这个view可以是自身，也可以是其他对象
        ImageView view = imageList.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(imageList.get(position));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return goodsList.get(position).name;
    }
}
