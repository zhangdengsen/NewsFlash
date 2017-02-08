package com.example.v5188.NewsFlash;


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
import android.widget.TextView;

public class ViewPagerFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private String[] arrayList = {"T1348647909107", "T1348648517839", "T1348649079062", "T1348648756099", "T1348649580692"};
    private TextView mTextView_toutiao, mTextView_yule, mTextView_tiyu, mTextView_caijing, mTextView_keji;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_pager, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewPager = (ViewPager) getView().findViewById(R.id.ViewPager);
        mTextView_toutiao = (TextView) getView().findViewById(R.id.TextView_toutiao);
        mTextView_yule = (TextView) getView().findViewById(R.id.TextView_yule);
        mTextView_tiyu = (TextView) getView().findViewById(R.id.TextView_tiyu);
        mTextView_caijing = (TextView) getView().findViewById(R.id.TextView_caijing);
        mTextView_keji = (TextView) getView().findViewById(R.id.TextView_keji);

        mTextView_toutiao.setBackgroundResource(R.drawable.btn_common_disabled);
        mTextView_toutiao.setTextColor(Color.WHITE);
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(getFragmentManager());
        myViewPagerAdapter.setDataLists(arrayList);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(this);
        mTextView_toutiao.setOnClickListener(this);
        mTextView_yule.setOnClickListener(this);
        mTextView_tiyu.setOnClickListener(this);
        mTextView_caijing.setOnClickListener(this);
        mTextView_keji.setOnClickListener(this);

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
            case R.id.TextView_keji:
                viewPager.setCurrentItem(4);
                mTextView_keji.setBackgroundResource(R.drawable.btn_common_disabled);
                mTextView_keji.setTextColor(Color.WHITE);
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
            case 4:
                mTextView_keji.setBackgroundResource(R.drawable.btn_common_disabled);
                mTextView_keji.setTextColor(Color.WHITE);
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
        mTextView_keji.setBackgroundColor(getResources().getColor(android.R.color.white));

        mTextView_toutiao.setTextColor(Color.GRAY);
        mTextView_yule.setTextColor(Color.GRAY);
        mTextView_tiyu.setTextColor(Color.GRAY);
        mTextView_caijing.setTextColor(Color.GRAY);
        mTextView_keji.setTextColor(Color.GRAY);
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
            return ListViewFragment.newInstance(list[position], position);
        }

        @Override
        public int getCount() {
            return list.length;
        }
    }
}
