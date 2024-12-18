package com.example.chapter05;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class DialogActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private TextView tv_dialog;
    private TimePicker time_picker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dialog);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btn_dialog).setOnClickListener(this);
        tv_dialog = findViewById(R.id.tv_dialog);

        findViewById(R.id.btn_set_date).setOnClickListener(this);

        findViewById(R.id.btn_show_date_dialog).setOnClickListener(this);

        findViewById(R.id.btn_show_time).setOnClickListener(this);

        time_picker = findViewById(R.id.tp_time);
        time_picker.setIs24HourView(true);

        findViewById(R.id.btn_show_time_picker).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_dialog) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("System alert");
            builder.setMessage("Do you want to log out?");
            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tv_dialog.setText("I have logged out");
                }
            });
            builder.setNegativeButton("no", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tv_dialog.setText("I will do nothing");
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else if (v.getId() == R.id.btn_set_date) {
            DatePicker picker = findViewById(R.id.dp_date);
            String desc = String.format("you choosed %s-%s-%s", picker.getYear(), picker.getMonth() + 1, picker.getDayOfMonth());
            TextView tv_date = findViewById(R.id.tv_date);
            tv_date.setText(desc);
        } else if (v.getId() == R.id.btn_show_date_dialog) {
            DatePickerDialog dialog = new DatePickerDialog(this, this, 2024, 12, 17);
            dialog.show();
        } else if (v.getId() == R.id.btn_show_time) {
            String desc = String.format("You choosed time: %s:%s", time_picker.getHour(), time_picker.getMinute());
            TextView tv_time = findViewById(R.id.tv_time);
            tv_time.setText(desc);
        } else if (v.getId() == R.id.btn_show_time_picker) {
            Calendar calendar = Calendar.getInstance();
            TimePickerDialog dialog = new TimePickerDialog(this, android.app.AlertDialog.THEME_HOLO_LIGHT, this, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
            dialog.show();
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String desc = String.format("You choosed %s-%s-%s", year, month + 1, dayOfMonth);
        TextView tv_date = findViewById(R.id.tv_date);
        tv_date.setText(desc);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String desc = String.format("You choosed time: %s:%s", hourOfDay, minute);
        TextView tv_time = findViewById(R.id.tv_time);
        tv_time.setText(desc);
    }
}