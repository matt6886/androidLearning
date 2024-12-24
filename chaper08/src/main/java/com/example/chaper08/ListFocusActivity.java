package com.example.chaper08;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chaper08.adapter.BasePlanetWithButtonAdapter;
import com.example.chaper08.bean.Planet;
import com.example.chaper08.util.ToastUtil;

import java.util.ArrayList;

public class ListFocusActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayList<Planet> planetList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_focus);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView lv_planet = findViewById(R.id.lv_planet);
        planetList = Planet.getDefaultList();
        BasePlanetWithButtonAdapter adapter = new BasePlanetWithButtonAdapter(this, planetList);
        lv_planet.setAdapter(adapter);
        lv_planet.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.show(this, "You clicked " + planetList.get(position).name);
    }
}