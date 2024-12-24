package com.example.chaper08.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chaper08.R;
import com.example.chaper08.bean.Planet;

import java.util.ArrayList;

public class BasePlanetAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Planet> planetList;

    public BasePlanetAdapter(Context mContext, ArrayList<Planet> planetList) {
        this.mContext = mContext;
        this.planetList = planetList;
    }

    @Override
    public int getCount() {
        return planetList.size();
    }

    @Override
    public Object getItem(int position) {
        return planetList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list, null);
            holder.iv_icon = convertView.findViewById(R.id.iv_icon);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_desc = convertView.findViewById(R.id.tv_desc);
            // 将视图持有者保存到转换视图当中
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Planet p = planetList.get(position);
        holder.iv_icon.setImageResource(p.image);
        holder.tv_name.setText(p.name);
        holder.tv_desc.setText(p.desc);
        return convertView;
    }

    public final class ViewHolder {
        public ImageView iv_icon;
        public TextView tv_name;
        public TextView tv_desc;
    }
}
