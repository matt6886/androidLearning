package com.example.chaper08;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.chaper08.adapter.ImagePagerAdapter;
import com.example.chaper08.bean.GoodsInfo;
import com.example.chaper08.util.ToastUtil;

import java.util.ArrayList;

public class ViewPagerActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ArrayList<GoodsInfo> goodsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_pager);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ViewPager vp_content = findViewById(R.id.vp_content);
        goodsList = GoodsInfo.getDefaultList();
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, goodsList);
        vp_content.setAdapter(adapter);
        vp_content.addOnPageChangeListener(this);
    }

    // 翻页状态改变时触发，state取值0表示静止，1表示正在滑动，2表示滑动完毕
    // 翻译过程，状态值变化依次为正在滑动->滑动结束->静止
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    // 在翻页过程中触发，第一个参数表示当前页面的参数，
    // 第二个参数表示偏移的百分比，取值从0-1，
    // 第三个参数表示页面的偏移距离
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    // 翻页结束后触发，表示当前滑动到了哪一页
    @Override
    public void onPageSelected(int position) {
        ToastUtil.show(this, "当前的手机品牌是" + goodsList.get(position).name);
    }


}