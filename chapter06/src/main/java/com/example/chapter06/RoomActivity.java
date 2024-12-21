package com.example.chapter06;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chapter06.dao.BookDao;
import com.example.chapter06.entity.BookInfo;
import com.example.chapter06.entity.User;
import com.example.chapter06.util.ToastUtil;

import java.util.List;

public class RoomActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView et_name;
    private TextView et_author;
    private TextView et_press;
    private TextView et_price;
    private BookDao bookDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_room);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et_name = findViewById(R.id.et_name);
        et_author = findViewById(R.id.et_author);
        et_press = findViewById(R.id.et_press);
        et_price = findViewById(R.id.et_price);

        findViewById(R.id.btn_create).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_retrieve).setOnClickListener(this);

        bookDao = MyApplication.getInstance().getBookDatabase().bookDao();
    }

    @Override
    public void onClick(View v) {
        String name = et_name.getText().toString();
        String author = et_author.getText().toString();
        String press = et_press.getText().toString();
        String price = et_price.getText().toString();
        if (v.getId() == R.id.btn_create) {
            BookInfo b1 = new BookInfo();
            b1.setName(name);
            b1.setAuthor(author);
            b1.setPress(press);
            b1.setPrice(Double.parseDouble(price));
            bookDao.insert(b1);
            ToastUtil.show(this, "inserted");
        } else if(v.getId() == R.id.btn_delete) {
            BookInfo b2 = new BookInfo();
            b2.setId(1);
            bookDao.delete(b2);
        } else if(v.getId() == R.id.btn_update) {
            BookInfo b3 = bookDao.queryByName(name);
            BookInfo b4 = new BookInfo();
            b4.setId(b3.getId());
            b4.setName(name);
            b4.setAuthor(author);
            b4.setPress(press);
            b4.setPrice(Double.parseDouble(price));
            bookDao.update(b4);
        } else if(v.getId() == R.id.btn_retrieve) {
            List<BookInfo> list = bookDao.queryAll();
            for(BookInfo b: list) {
                Log.d("Matt", b.toString());
            }
        }
    }
}