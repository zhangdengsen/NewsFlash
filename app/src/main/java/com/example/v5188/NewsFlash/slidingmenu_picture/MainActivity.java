package com.example.v5188.NewsFlash.slidingmenu_picture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.v5188.NewsFlash.R;
import com.example.v5188.NewsFlash.slidingmenu_video.ViewPagerFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        int yema = intent.getIntExtra("yema", 0);
        switch (yema) {
            case 1:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.FrameLayout, new TabViewPagerFragmen())
                        .commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.FrameLayout, new ViewPagerFragment())
                        .commit();
                break;
        }

    }
}
