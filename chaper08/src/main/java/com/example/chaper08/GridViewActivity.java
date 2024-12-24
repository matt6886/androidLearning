package com.example.chaper08;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chaper08.adapter.BasePlanetGridtAdapter;
import com.example.chaper08.bean.Planet;
import com.example.chaper08.util.ToastUtil;

import java.util.ArrayList;

public class GridViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private GridView gv_planet;
    private ArrayList<Planet> planetList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grid_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        planetList = Planet.getDefaultList();
        BasePlanetGridtAdapter adapter = new BasePlanetGridtAdapter(this, planetList);
        gv_planet = findViewById(R.id.gv_planet);
        gv_planet.setAdapter(adapter);
        gv_planet.setOnItemClickListener(this);
        gv_planet.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.show(this, "You clicked " + planetList.get(position).name);
    }
}