package com.example.v5188.NewsFlash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyErrorHelper;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.example.v5188.NewsFlash.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContentDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private String docid;
    private ImageView mNewsInformationBackImg;
    private NetworkImageView mNetworkImageView;
    private TextView mNewsTextTitleTxt, mNewsTextSourceAndPtimeTxt, mNewsTextImgNoTxt, mNewsTextinFormationTxt;
    ArrayList<String> list = new ArrayList<>();
    RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        mNewsInformationBackImg = (ImageView) findViewById(R.id.news_information_back);
        mNetworkImageView = (NetworkImageView) findViewById(R.id.news_text_img);
        mNewsTextTitleTxt = (TextView) findViewById(R.id.news_text_title);
        mNewsTextSourceAndPtimeTxt = (TextView) findViewById(R.id.news_text_source_and_ptime);
        mNewsTextImgNoTxt = (TextView) findViewById(R.id.news_text_img_no);
        mNewsTextinFormationTxt = (TextView) findViewById(R.id.news_text_information);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout);
        mNewsInformationBackImg.setOnClickListener(this);
        mNetworkImageView.setOnClickListener(this);
        getBundle();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.news_text_img:
                startActivity(new Intent(this, PictureDetailsActivity.class).putStringArrayListExtra("list", list));
                break;
            case R.id.news_information_back:
                finish();
                break;
        }
    }

    public void getBundle() {
        Intent intent = getIntent();
        docid = intent.getStringExtra("TEXT_NEWS");
        String txtUrl = "http://c.m.163.com/nc/article/" + docid + "/full.html";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, txtUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        getGson(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ContentDetailsActivity.this, VolleyErrorHelper.getMessage(error, ContentDetailsActivity.this), Toast.LENGTH_SHORT).show();
            }
        });
        RequestManager.addRequest(stringRequest, this);
    }

    public void getGson(String content) {
        JSONObject JSONObject;
        try {
            JSONObject = new JSONObject(content);
            JSONObject json = JSONObject.getJSONObject(docid);
            JSONArray array = json.getJSONArray("img");
            String title = json.getString("title");
            String source = json.getString("source");
            String ptime = json.getString("ptime");
            String body = json.getString("body");
            mNewsTextTitleTxt.setText(title);
            mNewsTextSourceAndPtimeTxt.setText("来源：" + source + " " + ptime);
            mNewsTextImgNoTxt.setText(String.valueOf(array.length()));
            mNewsTextinFormationTxt.setText(Html.fromHtml(body));


            for (int i = 0; i < array.length(); i++) {
                JSONObject objects = array.getJSONObject(i);
                String jpg = objects.getString("src");
                list.add(jpg);
            }
            JSONObject object = array.getJSONObject(0);
            mNetworkImageView.setDefaultImageResId(R.drawable.t010be2ad36b01e8f57);
            mNetworkImageView.setErrorImageResId(R.drawable.t017dd2114dfd5b9410);
            mNetworkImageView.setImageUrl(object.getString("src"), RequestManager.getImageLoader());
            mRelativeLayout.setVisibility(View.VISIBLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
