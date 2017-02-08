package com.example.v5188.NewsFlash;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class PictureDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    private ViewPager mViewPager;
    private ImageView mImageView;
    static ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        mViewPager = (ViewPager) findViewById(R.id.ViewPager_1);
        mImageView= (ImageView) findViewById(R.id.ImageView);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        list = getIntent().getStringArrayListExtra("list");
        MyImagePageradapter myImagePageradapter = new MyImagePageradapter(getSupportFragmentManager());
        myImagePageradapter.setList(list);
        mViewPager.setAdapter(myImagePageradapter);
        mImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    //第三步适配器
    class MyImagePageradapter extends FragmentStatePagerAdapter {
        private ArrayList<String> list = new ArrayList<>();

        private void setList(ArrayList<String> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        public MyImagePageradapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return DetailsFragment.newInstance(list.get(position), position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
