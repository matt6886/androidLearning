package com.example.chaper08;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btn_spinner).setOnClickListener(this);
        findViewById(R.id.btn_simple_adapter).setOnClickListener(this);
        findViewById(R.id.btn_base_adapter).setOnClickListener(this);
        findViewById(R.id.btn_listview).setOnClickListener(this);
        findViewById(R.id.btn_list_focus).setOnClickListener(this);
        findViewById(R.id.btn_grid_view).setOnClickListener(this);
        findViewById(R.id.btn_view_pager).setOnClickListener(this);
        findViewById(R.id.btn_pager_tab).setOnClickListener(this);
        findViewById(R.id.btn_launcher).setOnClickListener(this);
        findViewById(R.id.btn_fragment_static).setOnClickListener(this);
        findViewById(R.id.btn_fragment_dynamic).setOnClickListener(this);
        findViewById(R.id.btn_launch_improvement).setOnClickListener(this);
        findViewById(R.id.btn_bill).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_spinner) {
            Intent intent = new Intent(this, SpinnerDropdownActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_simple_adapter) {
            Intent intent = new Intent(this, SimpleAdapterActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_base_adapter) {
            Intent intent = new Intent(this, BaseAdapterActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_listview) {
            Intent intent = new Intent(this, ListViewActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_list_focus) {
            Intent intent = new Intent(this, ListFocusActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_grid_view) {
            Intent intent = new Intent(this, GridViewActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_view_pager) {
            Intent intent = new Intent(this, ViewPagerActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_pager_tab) {
            Intent intent = new Intent(this, PagerTabActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_launcher) {
            Intent intent = new Intent(this, LauncherSimpleActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_fragment_static) {
            Intent intent = new Intent(this, FragmentStaticActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_fragment_dynamic) {
            Intent intent = new Intent(this, FragmentDynamicActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_launch_improvement) {
            Intent intent = new Intent(this, LaunchImprovementActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_bill) {
            Intent intent = new Intent(this, BillAddActivity.class);
            startActivity(intent);
        }
    }
}