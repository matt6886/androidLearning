package com.example.chaper08.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.chaper08.R;
import com.example.chaper08.util.ToastUtil;

import java.util.ArrayList;

public class LuanchPagerAdapter extends PagerAdapter {
    private Context mContent;
    private int[] imageArray;

    private ArrayList<View> mViewList = new ArrayList<>();

    public LuanchPagerAdapter(Context mContent, int[] imageArray) {
        this.mContent = mContent;
        this.imageArray = imageArray;
        for (int i = 0; i < imageArray.length; i++) {
            View view = LayoutInflater.from(mContent).inflate(R.layout.item_launch, null);
            ImageView iv_launcher = view.findViewById(R.id.iv_launcher);
            RadioGroup rg_indicate = view.findViewById(R.id.rg_indicate);
            Button btn_start = view.findViewById(R.id.btn_start);

            for (int j = 0; j < imageArray.length; j++) {
                RadioButton radio = new RadioButton(mContent);
                radio.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                radio.setPadding(10, 10, 10, 10 );
                rg_indicate.addView(radio);
            }

            ((RadioButton)rg_indicate.getChildAt(i)).setChecked(true);

            if (i == imageArray.length - 1) {
                btn_start.setVisibility(View.VISIBLE);
                btn_start.setOnClickListener(v -> {
                    ToastUtil.show(mContent, "欢迎开始美好生活");
                });
            }

            iv_launcher.setImageResource(imageArray[i]);
            mViewList.add(view);
        }
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = mViewList.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mViewList.get(position));
    }
}
