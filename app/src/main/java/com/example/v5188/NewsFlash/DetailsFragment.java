package com.example.v5188.NewsFlash;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.v5188.NewsFlash.volley.RequestManager;

public class DetailsFragment extends Fragment {
    private String url;
    private int number;

    public static DetailsFragment newInstance(String url, int number) {
        Bundle args = new Bundle();
        args.putString("url", url);
        args.putInt("number", number + 1);
        DetailsFragment fragment = new DetailsFragment();
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
        return inflater.inflate(R.layout.fragment_xiang_qing, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NetworkImageView networkImageView = (NetworkImageView) getView().findViewById(R.id.ImageView);
        TextView textView1 = (TextView) getView().findViewById(R.id.TextView1);
        TextView textView2 = (TextView) getView().findViewById(R.id.TextView2);

        networkImageView.setDefaultImageResId(R.drawable.t010be2ad36b01e8f57);
        networkImageView.setErrorImageResId(R.drawable.t017dd2114dfd5b9410);
        networkImageView.setImageUrl(url, RequestManager.getImageLoader());

        textView1.setText(number + "");
        textView2.setText(PictureDetailsActivity.list.size() + "");
    }
}
