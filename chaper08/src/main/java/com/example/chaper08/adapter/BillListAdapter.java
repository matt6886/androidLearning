package com.example.chaper08.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chaper08.R;
import com.example.chaper08.bean.BillInfo;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BillListAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<BillInfo> billList;

    public BillListAdapter(Context context, ArrayList<BillInfo> billList) {
        this.context = context;
        this.billList = billList;
    }

    @Override
    public int getCount() {
        return billList.size();
    }

    @Override
    public Object getItem(int position) {
        return billList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return billList.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bill, null);
            holder.tv_date = convertView.findViewById(R.id.tv_date);
            holder.tv_remark = convertView.findViewById(R.id.tv_remark);
            holder.tv_amount = convertView.findViewById(R.id.tv_amount);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BillInfo bill = billList.get(position);
        holder.tv_date.setText(bill.date);
        holder.tv_remark.setText(bill.remark);
        holder.tv_amount.setText(String.format("%s%d", bill.type == 0 ? "+" : "-", (int)bill.amount));
        return convertView;
    }

    public final class ViewHolder {
        TextView tv_date;
        TextView tv_remark;
        TextView tv_amount;
    }
}
