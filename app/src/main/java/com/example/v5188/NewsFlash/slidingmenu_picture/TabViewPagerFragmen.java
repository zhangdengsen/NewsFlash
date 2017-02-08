package com.example.v5188.NewsFlash.slidingmenu_picture;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.v5188.NewsFlash.R;

public class TabViewPagerFragmen extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private String jinxuan_tupian = "http://api.sina.cn/sinago/list.json?channel=hdpic_toutiao&adid=4ad30dabe134695c3b7c3a65977d7e72&wm=b207&from=6042095012&chwm=12050_0001&oldchwm=&imei=867064013906290&uid=802909da86d9f5fc&p=";
    private String qutu_tupian = "http://api.sina.cn/sinago/list.json?channel=hdpic_funny&adid=4ad30dabe134695c3b7c3a65977d7e72&wm=b207&from=6042095012&chwm=12050_0001&oldchwm=12050_0001&imei=867064013906290&uid=802909da86d9f5fc&p=";
    private String meitu_tupian = "http://api.sina.cn/sinago/list.json?channel=hdpic_pretty&adid=4ad30dabe134695c3b7c3a65977d7e72&wm=b207&from=6042095012&chwm=12050_0001&oldchwm=12050_0001&imei=867064013906290&uid=802909da86d9f5fc&p=";
    private String gushi_tupian = "http://api.sina.cn/sinago/list.json?channel=hdpic_story&adid=4ad30dabe134695c3b7c3a65977d7e72&wm=b207&from=6042095012&chwm=12050_0001&oldchwm=12050_0001&imei=867064013906290&uid=802909da86d9f5fc&p=";
    private String[] list = {jinxuan_tupian, qutu_tupian, meitu_tupian, gushi_tupian};
    private TextView mTextView_toutiao, mTextView_yule, mTextView_tiyu, mTextView_caijing;
    private ViewPager viewPager;
    private ImageView mImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tab_view_pager, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewPager = (ViewPager) getView().findViewById(R.id.ViewPager);
        mTextView_toutiao = (TextView) getView().findViewById(R.id.TextView_toutiao);
        mTextView_yule = (TextView) getView().findViewById(R.id.TextView_yule);
        mTextView_tiyu = (TextView) getView().findViewById(R.id.TextView_tiyu);
        mTextView_caijing = (TextView) getView().findViewById(R.id.TextView_caijing);
        mImageView = (ImageView) getView().findViewById(R.id.ImageView);

        mTextView_toutiao.setBackgroundResource(R.drawable.btn_common_disabled);
        mTextView_toutiao.setTextColor(Color.WHITE);
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(getFragmentManager());
        myViewPagerAdapter.setDataLists(list);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(this);
        mTextView_toutiao.setOnClickListener(this);
        mTextView_yule.setOnClickListener(this);
        mTextView_tiyu.setOnClickListener(this);
        mTextView_caijing.setOnClickListener(this);
        mImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        clearBackgroundColor();
        switch (view.getId()) {
            case R.id.TextView_toutiao:
                viewPager.setCurrentItem(0);
                mTextView_toutiao.setBackgroundResource(R.drawable.btn_common_disabled);
                mTextView_toutiao.setTextColor(Color.WHITE);
                break;
            case R.id.TextView_yule:
                viewPager.setCurrentItem(1);
                mTextView_yule.setBackgroundResource(R.drawable.btn_common_disabled);
                mTextView_yule.setTextColor(Color.WHITE);
                break;
            case R.id.TextView_tiyu:
                viewPager.setCurrentItem(2);
                mTextView_tiyu.setBackgroundResource(R.drawable.btn_common_disabled);
                mTextView_tiyu.setTextColor(Color.WHITE);
                break;
            case R.id.TextView_caijing:
                viewPager.setCurrentItem(3);
                mTextView_caijing.setBackgroundResource(R.drawable.btn_common_disabled);
                mTextView_caijing.setTextColor(Color.WHITE);
                break;
            case R.id.ImageView:
                getActivity().finish();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        clearBackgroundColor();
        switch (position) {
            case 0:
                mTextView_toutiao.setBackgroundResource(R.drawable.btn_common_disabled);
                mTextView_toutiao.setTextColor(Color.WHITE);
                break;
            case 1:
                mTextView_yule.setBackgroundResource(R.drawable.btn_common_disabled);
                mTextView_yule.setTextColor(Color.WHITE);
                break;
            case 2:
                mTextView_tiyu.setBackgroundResource(R.drawable.btn_common_disabled);
                mTextView_tiyu.setTextColor(Color.WHITE);
                break;
            case 3:
                mTextView_caijing.setBackgroundResource(R.drawable.btn_common_disabled);
                mTextView_caijing.setTextColor(Color.WHITE);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void clearBackgroundColor() {
        mTextView_toutiao.setBackgroundColor(getResources().getColor(android.R.color.white));
        mTextView_yule.setBackgroundColor(getResources().getColor(android.R.color.white));
        mTextView_tiyu.setBackgroundColor(getResources().getColor(android.R.color.white));
        mTextView_caijing.setBackgroundColor(getResources().getColor(android.R.color.white));

        mTextView_toutiao.setTextColor(Color.GRAY);
        mTextView_yule.setTextColor(Color.GRAY);
        mTextView_tiyu.setTextColor(Color.GRAY);
        mTextView_caijing.setTextColor(Color.GRAY);
    }

    public class MyViewPagerAdapter extends FragmentStatePagerAdapter {
        String[] list = new String[]{};

        private void setDataLists(String[] list) {
            this.list = list;
            notifyDataSetChanged();
        }

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PictureFragment.newInstance(list[position], position);
        }

        @Override
        public int getCount() {
            return list.length;
        }
    }
}
