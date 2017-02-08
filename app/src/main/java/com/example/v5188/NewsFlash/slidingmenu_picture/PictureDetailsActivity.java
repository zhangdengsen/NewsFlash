package com.example.v5188.NewsFlash.slidingmenu_picture;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyErrorHelper;
import com.android.volley.toolbox.StringRequest;
import com.example.v5188.NewsFlash.R;
import com.example.v5188.NewsFlash.slidingmenu_picture.json.ListData;
import com.example.v5188.NewsFlash.slidingmenu_picture.details.Data;
import com.example.v5188.NewsFlash.slidingmenu_picture.details.PicsList;
import com.example.v5188.NewsFlash.slidingmenu_picture.details.Status;
import com.example.v5188.NewsFlash.volley.RequestManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PictureDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager mViewPager;
    private ImageView mImageView;
    private  MyImagePageradapter myImagePageradapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mViewPager = (ViewPager) findViewById(R.id.ViewPager);
        mImageView = (ImageView) findViewById(R.id.ImageView);
        myImagePageradapter = new MyImagePageradapter(getSupportFragmentManager());
        mViewPager.setAdapter(myImagePageradapter);
        getDataLists();
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    //第三步适配器
    class MyImagePageradapter extends FragmentStatePagerAdapter {
        private List<PicsList> picsList = new ArrayList<>();

        private void setPicsList(List<PicsList> picsList) {
            this.picsList = picsList;
            notifyDataSetChanged();
        }

        public MyImagePageradapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ViewPagerFragment.newInstance(picsList, position);
        }

        @Override
        public int getCount() {
            return picsList.size();
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

    /**
     * 网络取文本和图片、Json解析
     */
    public void getDataLists() {
        ListData listData = PictureFragment.listData;
        String id = listData.getId();
        String baseUrl = "http://api.sina.cn/sinago/article.json?postt=hdpic_hdpic_toutiao_4&wm=b207&from=6042095012&chwm=12050_0001&oldchwm=12050_0001&imei=867064013906290&uid=802909da86d9f5fc&id=" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Status status = gson.fromJson(response, Status.class);
                        Data data = status.getData();
                        List<PicsList> picsList = data.getPics();
                        myImagePageradapter.setPicsList(picsList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PictureDetailsActivity.this, VolleyErrorHelper.getMessage(error, PictureDetailsActivity.this), Toast.LENGTH_SHORT).show();
            }
        });
        RequestManager.addRequest(stringRequest, this);


    }
}
