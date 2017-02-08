package com.example.v5188.NewsFlash;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyErrorHelper;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.example.v5188.NewsFlash.json.NewsClass;
import com.example.v5188.NewsFlash.json.NewsList;
import com.example.v5188.NewsFlash.json.NewThreePictures;
import com.example.v5188.NewsFlash.json.NewsPicture;
import com.example.v5188.NewsFlash.volley.RequestManager;
import com.google.gson.Gson;
import com.scxh.slider.library.SliderLayout;
import com.scxh.slider.library.SliderTypes.BaseSliderView;
import com.scxh.slider.library.SliderTypes.TextSliderView;
import com.warmtel.xlistview.XListView;

import java.util.ArrayList;
import java.util.List;

public class ListViewFragment extends Fragment implements XListView.IXListViewListener, XListView.OnItemClickListener {
    private int pageNo = 0; //页号 ，表示第几页,第一页从0开始
    private int mTotalPageCount = 10; //总页数
    private int pageSize = 20; //页大小，显示每页多少条数据
    private XListView mXListView;
    private MyPagerAdapter mPagerAdapter;
    private SliderLayout mSliderLayout;
    private int number;
    private String url;
    private List<NewsList> list;
    private NewsList newsList;

    public static ListViewFragment newInstance(String url, int number) {
        Bundle args = new Bundle();
        args.putString("url", url);
        args.putInt("number", number);
        ListViewFragment fragment = new ListViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("url");
        number = getArguments().getInt("number");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mXListView = (XListView) getView().findViewById(R.id.XListView);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.slide_layout, null);
        mSliderLayout = (SliderLayout) view.findViewById(R.id.SliderLayout);
        mPagerAdapter = new MyPagerAdapter(getContext());
        mXListView.addHeaderView(view);
        mXListView.setAdapter(mPagerAdapter);
        mXListView.setPullLoadEnable(true); //上拉加载更多开关
        mXListView.setPullRefreshEnable(true);//下拉刷新开关

        mXListView.setXListViewListener(this);
        mXListView.setOnItemClickListener(this);
        getDataLists(pageNo);
    }

    @Override
    public void onRefresh() {
//        list.clear();
        pageNo = 0;
        getDataLists(pageNo);
    }

    @Override
    public void onLoadMore() {
        if (++pageNo > mTotalPageCount) {
            pageNo = mTotalPageCount;
            mXListView.stopLoadMore();
            Toast.makeText(getContext(), "已加载到最后一页", Toast.LENGTH_SHORT).show();
            return;
        }
        getDataLists(pageNo);
    }

    /**
     * 网络取文本和图片、Json解析
     *
     * @param pageNo
     */
    public void getDataLists(int pageNo) {
        String type;
        if (number == 0) {
            type = "headline/";
        } else {
            type = "list/";
        }
        String baseUrl = "http://c.m.163.com/nc/article/" + type + url + "/" + pageNo * pageSize + "-" + pageSize + ".html";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mXListView.stopLoadMore();
                        mXListView.stopRefresh();
                        mXListView.setRefreshTime("刚刚");
                        Gson gson = new Gson();
                        NewsClass newsClass = gson.fromJson(response, NewsClass.class);
                        switch (number) {
                            case 0:
                                list = newsClass.getT1348647909107();
                                break;
                            case 1:
                                list = newsClass.getT1348648517839();
                                break;
                            case 2:
                                list = newsClass.getT1348649079062();
                                break;
                            case 3:
                                list = newsClass.getT1348648756099();
                                break;
                            case 4:
                                list = newsClass.getT1348649580692();
                                break;
                        }
                        List<NewsPicture> tupian = list.get(0).getAds();
                        if (mSliderLayout != null) {
                            mSliderLayout.removeAllSliders();
                        }
                        if (tupian!=null) {
                            for (NewsPicture newTuPian : tupian) {
                                TextSliderView textSliderView = new TextSliderView(getContext());
                                textSliderView
                                        .description(newTuPian.getTitle())
                                        .image(newTuPian.getImgsrc())
                                        .setScaleType(BaseSliderView.ScaleType.Fit);
                                mSliderLayout.addSlider(textSliderView);
                            }
                        }
                        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
                        mPagerAdapter.setDataList(list);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), VolleyErrorHelper.getMessage(error, getContext()), Toast.LENGTH_SHORT).show();
            }
        });
        RequestManager.addRequest(stringRequest, this);


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        newsList = (NewsList) adapterView.getAdapter().getItem(i);
        String url = newsList.getDocid();
        startActivity(new Intent(getContext(), ContentDetailsActivity.class).putExtra("TEXT_NEWS", url));
    }

    /**
     * 适配器
     */
    public class MyPagerAdapter extends BaseAdapter {
        static final int ITEM_TYPE_ONE = 0; //三张图片
        static final int ITEM_TYPE_TWO = 1;
        List<NewsList> list = new ArrayList<>();
        LayoutInflater layoutInflater;

        public MyPagerAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        public void setDataList(List<NewsList> list) {
            if (pageNo == 0) {
                this.list = list;
            } else {
                this.list.addAll(list);
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            NewsList newsList = (NewsList) getItem(position);
            if (newsList.getImgextra() != null) {
                return ITEM_TYPE_ONE;
            } else {
                return ITEM_TYPE_TWO;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int type = getItemViewType(position);
            if (type == ITEM_TYPE_TWO) {
                return getOneView(position, convertView, parent);
            } else {
                return getTwoView(position, convertView, parent);
            }
        }

        public View getOneView(int position, View convertView, ViewGroup parent) {
            Messagess messages;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.tupian, null);
                NetworkImageView tupian = (NetworkImageView) convertView.findViewById(R.id.ImageView_tupian);
                TextView biaoti = (TextView) convertView.findViewById(R.id.TextView_biaoti);
                TextView neirong = (TextView) convertView.findViewById(R.id.TextView_neirong);
                messages = new Messagess();
                messages.tupian = tupian;
                messages.biati = biaoti;
                messages.neirong = neirong;
                convertView.setTag(messages);
            }
            messages = (Messagess) convertView.getTag();
            newsList = (NewsList) getItem(position);
            String dizhi = newsList.getImgsrc();

            messages.tupian.setDefaultImageResId(R.drawable.loading_pin);
            messages.tupian.setErrorImageResId(R.drawable.t017dd2114dfd5b9410);
            messages.tupian.setImageUrl(dizhi, RequestManager.getImageLoader());

            messages.biati.setText(newsList.getTitle());
            messages.neirong.setText(newsList.getDigest());
            return convertView;
        }

        public View getTwoView(int position, View convertView, ViewGroup parent) {
            ViewTwoHolder viewTwoHolder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.shanzhang_tupian, null);
                TextView biaoti = (TextView) convertView.findViewById(R.id.TextView);
                NetworkImageView leftImageView = (NetworkImageView) convertView.findViewById(R.id.ImageView1);
                NetworkImageView centerImageView = (NetworkImageView) convertView.findViewById(R.id.ImageView2);
                NetworkImageView rightImageView = (NetworkImageView) convertView.findViewById(R.id.ImageView3);

                viewTwoHolder = new ViewTwoHolder();
                viewTwoHolder.biaoti = biaoti;
                viewTwoHolder.leftImageView = leftImageView;
                viewTwoHolder.centerImageView = centerImageView;
                viewTwoHolder.rightImageView = rightImageView;
                convertView.setTag(viewTwoHolder);
            } else {
                viewTwoHolder = (ViewTwoHolder) convertView.getTag();
            }
            newsList = (NewsList) getItem(position);
            List<NewThreePictures> list1 = newsList.getImgextra();
            String dizhi1 = list1.get(0).getImgsrc();
            String dizhi2 = list1.get(1).getImgsrc();
            viewTwoHolder.biaoti.setText(newsList.getTitle());
            String dizhi = newsList.getImgsrc();

            viewTwoHolder.leftImageView.setDefaultImageResId(R.drawable.loading_pin);
            viewTwoHolder.leftImageView.setErrorImageResId(R.drawable.t017dd2114dfd5b9410);
            viewTwoHolder.leftImageView.setImageUrl(dizhi, RequestManager.getImageLoader());

            viewTwoHolder.centerImageView.setDefaultImageResId(R.drawable.loading_pin);
            viewTwoHolder.centerImageView.setErrorImageResId(R.drawable.t017dd2114dfd5b9410);
            viewTwoHolder.centerImageView.setImageUrl(dizhi1, RequestManager.getImageLoader());

            viewTwoHolder.rightImageView.setDefaultImageResId(R.drawable.loading_pin);
            viewTwoHolder.rightImageView.setErrorImageResId(R.drawable.t017dd2114dfd5b9410);
            viewTwoHolder.rightImageView.setImageUrl(dizhi2, RequestManager.getImageLoader());
            return convertView;
        }

        class Messagess {
            NetworkImageView tupian;
            TextView biati;
            TextView neirong;
        }

        class ViewTwoHolder {
            TextView biaoti;
            NetworkImageView leftImageView;
            NetworkImageView centerImageView;
            NetworkImageView rightImageView;
        }
    }

}
