package com.example.chaper08.bean;

public class BillInfo {
    public int id;
    public String date;
    public int type;
    public double amount;
    public String remark;

    public static final int BILL_TYPE_INCOME = 0;
    public static final int BIL_TYPE_COST = 1;

    @Override
    public String toString() {
        return "BillInfo{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                ", remark='" + remark + '\'' +
                '}';
    }
}
