package com.example.chaper08.bean;

import com.example.chaper08.R;

import java.util.ArrayList;

public class Planet {
    public int image;
    public String name;
    public String desc;

    public Planet(int image, String name, String desc) {
        this.image = image;
        this.name = name;
        this.desc = desc;
    }

    private static final String[] nameArray = new String[] {"水星", "金星", "地球", "火星", "木星", "土星"};
    private static final int[] iconArray = {
            R.drawable.shuixing, R.drawable.jinxing, R.drawable.earth, R.drawable.huoxing, R.drawable.muxing, R.drawable.tuxing
    };
    private static final String[] descArray = new String[] {"水星是太阳系的八大行星中最小和最靠近太阳的行星。轨道周期是87.9691 地球日，从地球上看，它大约116天左右与地球会合一次",
            "金星（拉丁语：Venus，天文符号：♀），在太阳系的八大行星中，第二近太阳之行星，轨道公转周期为224.7地球日，其无卫星",
            "地球是目前太阳系中以太阳为中心由内向外的第三颗行星，与太阳平均距离149,597,870公里（1天文单位），是目前宇宙中已知唯一存在生命的天体",
            "火星（拉丁语：Mars；天文符号：♂），是离太阳第四近的行星，也是太阳系中仅次于水星的第二小的行星，为太阳系里四颗类地行星之一。",
            "木星是距离太阳第五近的行星，也是太阳系中体积最大的行星，目前已知有95颗卫星。天文学家很早就发现了这颗行星[11] ",
            "土星，为太阳系八大行星之一，至太阳距离（由近到远）位于第六、体积则仅次于木星。并与木星同属气体（类木）巨行星。"};


    public static ArrayList<Planet> getDefaultList() {
        ArrayList<Planet> planetList = new ArrayList<>();
        for (int i = 0; i < iconArray.length; i++) {
            planetList.add(new Planet(iconArray[i], nameArray[i], descArray[i]));
        }
        return planetList;
    }
}
