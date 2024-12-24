package com.example.chaper08.bean;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.chaper08.R;
import com.example.chaper08.adapter.BillListAdapter;
import com.example.chaper08.adapter.BillPagerAdapter;
import com.example.chaper08.database.BillDBHelper;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BillFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillFragment extends Fragment {
    public static BillFragment newInstance(String yearMonth) {
        BillFragment fragment = new BillFragment();
        Bundle args = new Bundle();
        args.putString("yearMonth", yearMonth);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        Bundle args = getArguments();
        assert args != null;
        String yearMonth = args.getString("yearMonth");
        BillDBHelper mDBHelper = BillDBHelper.getInstance(getContext());
        ArrayList<BillInfo> billInfoList = mDBHelper.queryByMonth(yearMonth);

        BillListAdapter adapter = new BillListAdapter(getContext(), billInfoList);
        ListView lv_bill = view.findViewById(R.id.lv_bill);
        lv_bill.setAdapter(adapter);
        return view;
    }
}