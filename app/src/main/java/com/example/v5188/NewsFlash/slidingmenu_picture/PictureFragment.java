package com.example.v5188.NewsFlash.slidingmenu_picture;


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
import com.example.v5188.NewsFlash.R;
import com.example.v5188.NewsFlash.slidingmenu_picture.json.Data;
import com.example.v5188.NewsFlash.slidingmenu_picture.json.ListData;
import com.example.v5188.NewsFlash.slidingmenu_picture.json.Status;
import com.example.v5188.NewsFlash.volley.RequestManager;
import com.google.gson.Gson;
import com.warmtel.xlistview.XListView;

import java.util.ArrayList;
import java.util.List;


public class PictureFragment extends Fragment implements XListView.OnItemClickListener {
    private XListView mXListView;
    private MyBaseAdapter myBaseAdapter;
    static ListData listData;
    private String url;
    private int number;

    public static PictureFragment newInstance(String url, int number) {
        Bundle args = new Bundle();
        args.putString("url", url);
        args.putInt("number", number);
        PictureFragment fragment = new PictureFragment();
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
        return inflater.inflate(R.layout.fragment_tu_pian, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mXListView = (XListView) getView().findViewById(R.id.XListView);
        myBaseAdapter = new MyBaseAdapter(getContext());
        mXListView.setAdapter(myBaseAdapter);
        mXListView.setPullLoadEnable(false); //上拉加载更多开关
        mXListView.setPullRefreshEnable(false);   //下拉刷新开关
        mXListView.setOnItemClickListener(this);
        getDataLists();
    }

    /**
     * 网络取文本和图片、Json解析
     */
    public void getDataLists() {
        String baseUrl = url;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                mXListView.stopLoadMore();
                                mXListView.stopRefresh();
                                mXListView.setRefreshTime("刚刚");
                                Gson gson = new Gson();
                                Status status = gson.fromJson(response, Status.class);
                                if (status!=null) {
                                    Data data = status.getData();
                                    List<ListData> list = data.getList();
                                    myBaseAdapter.setList(list);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), VolleyErrorHelper.getMessage(error,getContext()), Toast.LENGTH_SHORT).show();
                    }
                });
                RequestManager.addRequest(stringRequest, this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        listData = (ListData) adapterView.getAdapter().getItem(i);
        startActivity(new Intent(getContext(), PictureDetailsActivity.class));
    }

    public class MyBaseAdapter extends BaseAdapter {
        List<ListData> list = new ArrayList<>();
        LayoutInflater layoutInflater;

        public MyBaseAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        public void setList(List<ListData> list) {
            this.list = list;
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
                view = layoutInflater.inflate(R.layout.jinxuan_tupian, null);
                NetworkImageView tupian = (NetworkImageView) view.findViewById(R.id.ImageView);
                TextView biaoti = (TextView) view.findViewById(R.id.TextView);
                messagess = new Messagess();
                messagess.tupian = tupian;
                messagess.biaoti = biaoti;
                view.setTag(messagess);
            }
            messagess = (Messagess) view.getTag();
            listData = (ListData) getItem(i);
            String jpg = listData.getKpic();

            messagess.tupian.setDefaultImageResId(R.drawable.t010be2ad36b01e8f57);
            messagess.tupian.setErrorImageResId(R.drawable.t017dd2114dfd5b9410);
            messagess.tupian.setImageUrl(jpg, RequestManager.getImageLoader());

            messagess.biaoti.setText(listData.getTitle());
            return view;
        }

        class Messagess {
            NetworkImageView tupian;
            TextView biaoti;
        }
    }
}
