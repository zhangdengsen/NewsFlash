package com.example.v5188.NewsFlash.slidingmenu_picture;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.v5188.NewsFlash.R;
import com.example.v5188.NewsFlash.slidingmenu_picture.json.ListData;
import com.example.v5188.NewsFlash.slidingmenu_picture.details.PicsList;
import com.example.v5188.NewsFlash.volley.RequestManager;

import java.util.List;

public class ViewPagerFragment extends Fragment {
    private String jpg;
    private String text;
    private int number;

    public static ViewPagerFragment newInstance(List<PicsList> picsList, int number) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle bundle;
        for (int i = 0; i < picsList.size(); i++) {
            if (number == i) {
                bundle = new Bundle();
                String jpg = picsList.get(i).getKpic();
                String text = picsList.get(i).getAlt();
                bundle.putString("jpg", jpg);
                bundle.putString("text", text);
                bundle.putInt("number", number + 1);
                fragment.setArguments(bundle);
            }
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jpg = getArguments().getString("jpg");
        text = getArguments().getString("text");
        number = getArguments().getInt("number");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_pager2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListData listData = PictureFragment.listData;
        NetworkImageView imageView = (NetworkImageView) getView().findViewById(R.id.ImageView);
        TextView textView = (TextView) getView().findViewById(R.id.TextView);
        TextView textView1 = (TextView) getView().findViewById(R.id.TextView_0);
        TextView textView2 = (TextView) getView().findViewById(R.id.TextView_3);
        TextView textView_biaoti = (TextView) getView().findViewById(R.id.TextView_biaoti);

        imageView.setDefaultImageResId(R.drawable.t010be2ad36b01e8f57);
        imageView.setErrorImageResId(R.drawable.t017dd2114dfd5b9410);
        imageView.setImageUrl(jpg, RequestManager.getImageLoader());

        textView.setText(text);
        textView_biaoti.setText(listData.getTitle());
        textView1.setText(number + "");
        textView2.setText(listData.getPics().getTotal() + "");
    }
}
