package com.example.v5188.NewsFlash.slidingmenu_video;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.v5188.NewsFlash.R;
import com.example.v5188.NewsFlash.slidingmenu_video.json.NewShiPing;
import com.example.v5188.NewsFlash.slidingmenu_video.json.V9LG4B3A0;
import com.example.v5188.NewsFlash.volley.RequestManager;
import com.google.gson.Gson;
import com.warmtel.xlistview.XListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShiPingFragment extends Fragment implements XListView.IXListViewListener, XListView.OnItemClickListener {
    private XListView mXListView;
    private int mTotalPageCount = 5; //总页数
    private int pageSize = 10; //页大小，显示每页多少条数据
    private int pageNo = 0; //页号 ，表示第几页,第一页从0开始
    private MyBaseAdapter myBaseAdapter;
    private V9LG4B3A0 v9LG4B3A0;
    private String url;
    private int number;
    private NewShiPing newShiPing;
    private List<V9LG4B3A0> list;

    public static ShiPingFragment newInstance(String url, int number) {
        Bundle args = new Bundle();
        args.putString("url", url);
        args.putInt("number", number);
        ShiPingFragment fragment = new ShiPingFragment();
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
        return inflater.inflate(R.layout.fragment_shi_ping, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mXListView = (XListView) getView().findViewById(R.id.XListView);
        myBaseAdapter = new MyBaseAdapter(getContext());
        mXListView.setAdapter(myBaseAdapter);
        mXListView.setPullLoadEnable(true); //上拉加载更多开关
        mXListView.setPullRefreshEnable(true);   //下拉刷新开关
        mXListView.setXListViewListener(this);
        mXListView.setOnItemClickListener(this);
        getDataLists(pageNo);
    }

    /**
     * 网络取文本和图片、Json解析
     *
     * @param pageNo
     */
    public void getDataLists(int pageNo) {
        String baseUrl = "http://c.3g.163.com/nc/video/list/" + url + "/n/" + pageNo * pageSize + "-" + pageSize + ".html";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mXListView.stopLoadMore();
                        mXListView.stopRefresh();
                        mXListView.setRefreshTime("刚刚");
                        Gson gson = new Gson();
                        newShiPing = gson.fromJson(response, NewShiPing.class);
                        switch (number) {
                            case 0:
                                list = newShiPing.getV9LG4B3A0();
                                break;
                            case 1:
                                list = newShiPing.getV9LG4CHOR();
                                break;
                            case 2:
                                list = newShiPing.getV9LG4E6VR();
                                break;
                            case 3:
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("00850FRB");
                                    List<V9LG4B3A0> ls = new ArrayList<V9LG4B3A0>();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject itemObject = jsonArray.getJSONObject(i);
                                        V9LG4B3A0 v9LG4B3A0 = new V9LG4B3A0();
                                        v9LG4B3A0.setTitle(itemObject.getString("title"));
                                        v9LG4B3A0.setCover(itemObject.getString("cover"));
                                        v9LG4B3A0.setMp4_url(itemObject.getString("mp4_url"));
                                        ls.add(v9LG4B3A0);
                                    }

                                    list = ls;

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                        myBaseAdapter.setList(list);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        v9LG4B3A0 = (V9LG4B3A0) adapterView.getAdapter().getItem(i);
        String mp4_url = v9LG4B3A0.getMp4_url();
        Uri uri = Uri.parse(mp4_url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "video/mp4");
        startActivity(intent);
    }

    public class MyBaseAdapter extends BaseAdapter {
        List<V9LG4B3A0> list = new ArrayList<>();
        LayoutInflater layoutInflater;

        public MyBaseAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        public void setList(List<V9LG4B3A0> list) {
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
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Messagess messagess;
            if (view == null) {
                view = layoutInflater.inflate(R.layout.shiping_tupian, null);
                NetworkImageView tupian = (NetworkImageView) view.findViewById(R.id.ImageView);
                TextView biaoti = (TextView) view.findViewById(R.id.TextView);
                messagess = new Messagess();
                messagess.tupian = tupian;
                messagess.biaoti = biaoti;
                view.setTag(messagess);
            }
            messagess = (Messagess) view.getTag();
            v9LG4B3A0 = (V9LG4B3A0) getItem(i);
            String jpg = v9LG4B3A0.getCover();

            messagess.tupian.setDefaultImageResId(R.drawable.t010be2ad36b01e8f57);
            messagess.tupian.setErrorImageResId(R.drawable.t017dd2114dfd5b9410);
            messagess.tupian.setImageUrl(jpg, RequestManager.getImageLoader());

            messagess.biaoti.setText(v9LG4B3A0.getTitle());
            return view;
        }

        class Messagess {
            NetworkImageView tupian;
            TextView biaoti;
        }
    }
}
