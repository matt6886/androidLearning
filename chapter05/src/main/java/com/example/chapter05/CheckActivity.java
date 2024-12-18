package com.example.chapter05;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CheckActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private TextView tv_switch;
    private TextView tv_switch_ck;
    TextView tv_radio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_check);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        CheckBox ck_system = findViewById(R.id.ck_default);
//        ck_system.setOnCheckedChangeListener(this);
//
//        CheckBox ck_custom = findViewById(R.id.ck_custom);
//        ck_custom.setOnCheckedChangeListener(this);

//        tv_switch = findViewById(R.id.tv_switch);
//        Switch sw_system = findViewById(R.id.sw_system);
//        sw_system.setOnCheckedChangeListener(this);
//
//        tv_switch_ck = findViewById(R.id.tv_switch_ck);
//        CheckBox ck_sw = findViewById(R.id.sw_custome);
//        ck_sw.setOnCheckedChangeListener(this);

        tv_radio = findViewById(R.id.tv_radio);

        RadioGroup rg = findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        String desc = String.format("you %s this checkbox", isChecked ? "checked" : "unchecked");
//        buttonView.setText(desc);

//        String desc = String.format("Switch is %s", isChecked ? "on" : "off");
//        tv_switch.setText(desc);

//        String desc = String.format("Custom Switch is: %s", isChecked ? "on" : "off");
//        tv_switch_ck.setText(desc);



    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rb_male) {
            String desc = "Your gender is male";
            tv_radio.setText(desc);
        } else if (v.getId() == R.id.rb_female) {
            String desc = "Your gender is female";
            tv_radio.setText(desc);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_male) {
            String desc = "Your gender is male";
            tv_radio.setText(desc);
        } else if (checkedId == R.id.rb_female) {
            String desc = "Your gender is female";
            tv_radio.setText(desc);
        }
    }
}