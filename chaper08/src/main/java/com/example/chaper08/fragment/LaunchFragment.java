package com.example.chaper08.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.chaper08.R;
import com.example.chaper08.util.ToastUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LaunchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LaunchFragment extends Fragment {

    public static LaunchFragment newInstance(int count, int position, int image_id) {
        LaunchFragment fragment = new LaunchFragment();
        Bundle args = new Bundle();
        args.putInt("count", count);
        args.putInt("position", position);
        args.putInt("image_id", image_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        assert args != null;
        int count = args.getInt("count", 4);
        int position = args.getInt("position", 0);
        int imageId = args.getInt("image_id", 0);
        Context context = getContext();
        View view = inflater.inflate(R.layout.fragment_launch, container, false);
        ImageView iv_launcher = view.findViewById(R.id.iv_launcher);
        RadioGroup rg_indicate = view.findViewById(R.id.rg_indicate);
        Button btn_start = view.findViewById(R.id.btn_start);
        iv_launcher.setImageResource(imageId);

        for (int j = 0; j < count; j++) {
            RadioButton radio = new RadioButton(context);
            radio.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            radio.setPadding(10, 10, 10, 10 );
            rg_indicate.addView(radio);
        }

        ((RadioButton)rg_indicate.getChildAt(position)).setChecked(true);

        if (position == count - 1) {
            btn_start.setVisibility(View.VISIBLE);
            btn_start.setOnClickListener(v -> {
                ToastUtil.show(context, "欢迎开始美好生活");
            });
        }
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("Matt", context.toString());
    }
}