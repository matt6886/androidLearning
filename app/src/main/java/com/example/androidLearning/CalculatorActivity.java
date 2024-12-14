package com.example.androidLearning;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private String firstNum = "";
    private String operator = "";
    private String secondNum = "";
    private String result = "";
    private String showText = "";

    TextView tv_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tv_result = findViewById(R.id.tv_res);

        Button btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        Button btn_divide = findViewById(R.id.btn_divide);
        btn_divide.setOnClickListener(this);
        Button btn_multiply = findViewById(R.id.btn_multiply);
        btn_multiply.setOnClickListener(this);
        Button btn_clear = findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(this);
        Button btn_seven = findViewById(R.id.btn_seven);
        btn_seven.setOnClickListener(this);
        Button btn_eight = findViewById(R.id.btn_eight);
        btn_eight.setOnClickListener(this);
        Button btn_nine = findViewById(R.id.btn_nine);
        btn_nine.setOnClickListener(this);
        Button btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        Button btn_four = findViewById(R.id.btn_four);
        btn_four.setOnClickListener(this);
        Button btn_five = findViewById(R.id.btn_five);
        btn_five.setOnClickListener(this);
        Button btn_six = findViewById(R.id.btn_six);
        btn_six.setOnClickListener(this);
        Button btn_minus = findViewById(R.id.btn_minus);
        btn_minus.setOnClickListener(this);
        Button btn_one = findViewById(R.id.btn_one);
        btn_one.setOnClickListener(this);
        Button btn_two = findViewById(R.id.btn_two);
        btn_two.setOnClickListener(this);
        Button btn_three = findViewById(R.id.btn_three);
        btn_three.setOnClickListener(this);
        Button btn_sqrt = findViewById(R.id.btn_sqrt);
        btn_sqrt.setOnClickListener(this);
        Button btn_reciprocal = findViewById(R.id.btn_reciprocal);
        btn_reciprocal.setOnClickListener(this);
        Button btn_zero = findViewById(R.id.btn_zero);
        btn_zero.setOnClickListener(this);
        Button btn_dot = findViewById(R.id.btn_dot);
        btn_dot.setOnClickListener(this);
        Button btn_equal = findViewById(R.id.btn_equal);
        btn_equal.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String inputText = ((TextView)(view)).getText().toString();
        int id = view.getId();
        if (id == R.id.btn_cancel) {
            clear();
        } else if (id == R.id.btn_clear) {
            clear();
        } else if (id == R.id.btn_add || id == R.id.btn_minus || id == R.id.btn_multiply || id == R.id.btn_divide) {
            // clear previous result
            operator = inputText;
            refreshText(showText + operator);
        } else if (id == R.id.btn_equal) {
            double calculate_result = calculateFour();
            refreshOperate(String.valueOf(calculate_result));
            refreshText(showText + "=" + result);

        } else if (id == R.id.btn_sqrt) {
            double sqrt_result = Math.sqrt(Double.parseDouble((firstNum)));
            refreshOperate(String.valueOf(sqrt_result));
            refreshText(showText + "√=" + result);
        } else if (id == R.id.btn_reciprocal) {
            double reciprocal_result = 1 / Double.parseDouble(firstNum);
            refreshOperate(String.valueOf(reciprocal_result));
            refreshText(showText + "/=" + result);
        } else {// . + numbers
            if (!result.isEmpty() && operator.isEmpty()) {
                clear();
            }
            if (operator.isEmpty()) {
                firstNum = firstNum + inputText;
            } else {
                secondNum = secondNum + inputText;
            }

            if (showText.equals("0") && !inputText.equals(".")) {
                refreshText(showText);
            } else {
                refreshText(showText + inputText);
            }
        }
    }

    private void refreshText(String text) {
        showText = text;
        tv_result.setText(showText);
    }

    private void clear() {
        refreshText("");
        refreshOperate("");
    }

    private void refreshOperate(String new_result) {
        result = new_result;
        firstNum = result;
        secondNum = "";
        operator = "";
    }

    private double calculateFour() {
        switch (operator) {
            case "+":
                return Double.parseDouble(firstNum) + Double.parseDouble(secondNum);
            case "-":
                return Double.parseDouble(firstNum) - Double.parseDouble(secondNum);
            case "×":
                return Double.parseDouble(firstNum) * Double.parseDouble(secondNum);
            default:
                return Double.parseDouble(firstNum) / Double.parseDouble(secondNum);
        }
    }

}