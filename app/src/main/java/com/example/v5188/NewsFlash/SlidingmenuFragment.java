package com.example.v5188.NewsFlash;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.v5188.NewsFlash.slidingmenu_picture.MainActivity;

public class SlidingmenuFragment extends Fragment implements View.OnClickListener {
    private TextView mTextView1, mTextView2, mTextView3,mTextView4,mTextView5;
    private Toggle mToggle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_slidingmenu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTextView1 = (TextView) getView().findViewById(R.id.TextView1);
        mTextView2 = (TextView) getView().findViewById(R.id.TextView2);
        mTextView3 = (TextView) getView().findViewById(R.id.TextView3);
        mTextView4 = (TextView) getView().findViewById(R.id.TextView4);
        mTextView5 = (TextView) getView().findViewById(R.id.TextView5);

        mTextView1.setOnClickListener(this);
        mTextView2.setOnClickListener(this);
        mTextView3.setOnClickListener(this);
        mTextView4.setOnClickListener(this);
        mTextView5.setOnClickListener(this);
    }

    interface Toggle {
        void toggle(View view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Toggle) {
            this.mToggle = (Toggle) context;
        } else {
            throw new ClassCastException("类型转换出错");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.TextView1:
                startActivity(new Intent(getContext(), MainActivity.class).putExtra("yema",1));
                break;
            case R.id.TextView2:
                startActivity(new Intent(getContext(), MainActivity.class).putExtra("yema",2));
                break;
            case R.id.TextView3:
                Toast.makeText(getContext(),"正在开发中...",Toast.LENGTH_SHORT).show();
                break;
            case R.id.TextView4:
                Toast.makeText(getContext(),"正在开发中...",Toast.LENGTH_SHORT).show();
                break;
            case R.id.TextView5:
                Toast.makeText(getContext(),"正在开发中...",Toast.LENGTH_SHORT).show();
                break;
        }
        mToggle.toggle(v);
    }
}
