package com.example.chaper08.bean;

import com.example.chaper08.R;

import java.util.ArrayList;

public class GoodsInfo {
    public int id;
    public String name;
    public String description;
    public float price;
    public String picPath;
    public int pic;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public String getPicPath() {
        return picPath;
    }

    public int getPic() {
        return pic;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    // declare goods name
    private static String[] mNameArray = {
            "iPhone11", "Mate30", "小米10", "OPPO Reno3", "vivo X30", "荣耀30S"
    };


    // declare goods description
    private static String[] mDescription = {
            "Apple iPhone11 256G black 4G",
        "Huawei Mate30 8G+256G gold 5G",
            "xiaomi MI10 8G+128G silver 5G",
            "OPPO Reno3 8G+128G 蓝色星夜 双模5G 拍照游戏5G手机",
            "vivo X30 8G+128G 飞云 5G全网 美颜拍照手机",
            "荣耀30S 8G+128G 爹雨虹 5G芯片 自拍全面平手机"
    };

    // declare goods price
    private static float[] mPrice = {
            6299, 4299, 3999, 2999, 2998, 2399
    };

    private static int[] mPicArray = {
            R.drawable.iphone, R.drawable.huawei, R.drawable.xiaomi,
            R.drawable.oppo, R.drawable.vivo, R.drawable.rongyao
    };

    public static ArrayList<GoodsInfo> getDefaultList() {
        ArrayList<GoodsInfo> goodsList = new ArrayList<>();
        for (int i = 0; i < mNameArray.length; i++) {
            GoodsInfo info = new GoodsInfo();
            info.id = i;
            info.name = mNameArray[i];
            info.description = mDescription[i];
            info.price = mPrice[i];
            info.pic = mPicArray[i];
            goodsList.add(info);
        }
        return goodsList;
    }
}
