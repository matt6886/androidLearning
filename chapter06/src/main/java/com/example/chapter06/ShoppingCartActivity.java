package com.example.chapter06;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chapter06.database.ShoppingDBHelper;
import com.example.chapter06.entity.CartInfo;
import com.example.chapter06.entity.GoodsInfo;
import com.example.chapter06.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCartActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_count;
    private LinearLayout ll_cart;
    private TextView tv_total_price;
    private LinearLayout ll_empty;
    private LinearLayout ll_content;
    private ShoppingDBHelper mdbHelper;
    private ArrayList<CartInfo> mCartList;

    private Map<Integer, GoodsInfo> mGoodsMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shopping_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("Cart");

        tv_count = findViewById(R.id.tv_count);
        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));
        ll_cart = findViewById(R.id.ll_cart);
        tv_total_price = findViewById(R.id.tv_total_price);

        ll_empty = findViewById(R.id.ll_empty);
        ll_content = findViewById(R.id.ll_content);

        mdbHelper = ShoppingDBHelper.getInstance(this);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.btn_shopping_channel).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);
        findViewById(R.id.btn_settle).setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        showCart();
    }

    // don't need to open it again, because the main page is still alive
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mdbHelper.openReadLink();
//        mdbHelper.openWriteLink();
//
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mdbHelper.closeLink();
//    }

    private void showCart() {
        // remove all child views
        ll_cart.removeAllViews();

        mCartList = mdbHelper.queryAllCartInfo();
        if (mCartList.isEmpty()) {
            return;
        }

        for (CartInfo cartInfo: mCartList) {
            GoodsInfo goods = mdbHelper.queryGoodsInfoById(cartInfo.goodsId);
            mGoodsMap.put(cartInfo.goodsId, goods);

            // export cart item form item_cart.xml
            View view = LayoutInflater.from(this).inflate(R.layout.item_cart, null);
            ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_desc = view.findViewById(R.id.tv_desc);
            TextView tv_count = view.findViewById(R.id.tv_count);
            TextView tv_price = view.findViewById(R.id.tv_price);
            TextView tv_sum = view.findViewById(R.id.tv_sum);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingCartActivity.this);
                    builder.setTitle("delete " + goods.name + "?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ll_cart.removeView(v);
                            // delete from cart info table
                            deleteGoods(cartInfo);
                        }
                    });
                    builder.setNegativeButton("No", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return true;
                }
            });

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ShoppingCartActivity.this, ShoppingDetailActivity.class);
                    intent.putExtra("goods_id", goods.id);
                    startActivity(intent);
                }
            });

            iv_thumb.setImageURI(Uri.parse(goods.picPath));
            tv_name.setText(goods.name);
            tv_desc.setText(goods.description);
            tv_price.setText(String.valueOf(goods.price));
            tv_count.setText(String.valueOf(cartInfo.count));
            tv_sum.setText(String.valueOf((int)cartInfo.count * goods.price));

            ll_cart.addView(view);
        }
        refreshTotalPrice();
    }

    private void deleteGoods(CartInfo cartInfo) {
        MyApplication.getInstance().goodsCount -= cartInfo.count;
        mdbHelper.deleteCartInfoByGoodsId(cartInfo.goodsId);
        // remove from cart list
        CartInfo toRemove = null;
        for (CartInfo info : mCartList) {
            if (info.goodsId == cartInfo.goodsId) {
                toRemove = info;
            }
        }
        mCartList.remove(toRemove);
        // refresh total goods ccount
        showCount();
        ToastUtil.show(this, "Delete " + mGoodsMap.get(cartInfo.goodsId).name + " From cart!");
        mGoodsMap.remove(cartInfo.goodsId);
        refreshTotalPrice();
    }

    private void showCount() {
        int count = MyApplication.getInstance().goodsCount;
        tv_count.setText(String.valueOf(count));
        if (count == 0) {
            ll_empty.setVisibility(View.VISIBLE);
            ll_content.setVisibility(View.GONE);
            ll_cart.removeAllViews();
        } else {
            ll_empty.setVisibility(View.GONE);
            ll_content.setVisibility(View.VISIBLE);
        }
    }

    private void refreshTotalPrice() {
        int totalPrice = 0;
        for (CartInfo cartInfo : mCartList) {
            GoodsInfo goodsInfo = mGoodsMap.get(cartInfo.goodsId);
            totalPrice += goodsInfo.price * cartInfo.count;
        }
        tv_total_price.setText(String.valueOf(totalPrice));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();
        } else if (v.getId() == R.id.btn_shopping_channel) {
            Intent intent = new Intent(this, ShoppingChannelActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_clear) {
            MyApplication.getInstance().goodsCount = 0;
            mdbHelper.deleteAllCartInfo();
            showCount();
            ToastUtil.show(this, "Cart is cleared!");
        } else if (v.getId() == R.id.btn_settle) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Settle goods");
            builder.setMessage("Payment function is not available now!");
            builder.setPositiveButton("OK", null);
            builder.create().show();
        }
    }
}