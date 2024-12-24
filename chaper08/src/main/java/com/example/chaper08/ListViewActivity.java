package com.example.chaper08;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chaper08.adapter.BasePlanetAdapter;
import com.example.chaper08.bean.Planet;
import com.example.chaper08.util.ToastUtil;
import com.example.chaper08.util.Utils;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener {

    private ArrayList<Planet> planetList;
    private CheckBox ck_divider;
    private CheckBox ck_selector;
    private ListView lv_planet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lv_planet = findViewById(R.id.lv_planet);
        planetList = Planet.getDefaultList();
        BasePlanetAdapter adapter = new BasePlanetAdapter(this, planetList);
        lv_planet.setAdapter(adapter);
        lv_planet.setOnItemClickListener(this);

        ck_divider = findViewById(R.id.ck_divider);
        ck_divider.setOnCheckedChangeListener(this);
        ck_selector = findViewById(R.id.ck_selector);
        ck_selector.setOnCheckedChangeListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.show(this, "You selected " + planetList.get(position).name);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.ck_divider) {
            if (isChecked) {
                Drawable drawable = getResources().getDrawable(R.color.black, getTheme());
                lv_planet.setDivider(drawable);
                int px = Utils.dip2px(this, 1);
                lv_planet.setDividerHeight(px);
            } else {
                lv_planet.setDivider(null);
                lv_planet.setDividerHeight(0);
            }
        } else if (buttonView.getId() == R.id.ck_selector) {
            if (isChecked) {
                lv_planet.setSelector(R.drawable.list_selector);
            } else {
                Drawable drawable = getResources().getDrawable(R.color.transparent, getTheme());
                lv_planet.setSelector(drawable);
            }
        }
    }
}