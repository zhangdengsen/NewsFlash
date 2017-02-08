package com.example.v5188.NewsFlash;

import android.os.Bundle;
import android.view.View;

import com.warmtel.slidingmenu.lib.SlidingMenu;
import com.warmtel.slidingmenu.lib.app.SlidingActivity;

public class MainActivity extends SlidingActivity implements SlidingmenuFragment.Toggle {
    private SlidingMenu slidingMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.FrameLayout, new ViewPagerFragment())
                .commit();
        setSlidingMenu();
    }

    /**
     * 侧滑菜单
     */
    public void setSlidingMenu() {
        setBehindContentView(R.layout.sliding_menu_layout);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.sliding_menu_layout, new SlidingmenuFragment())
                .commit();

        slidingMenu = getSlidingMenu();
        slidingMenu.setSlidingEnabled(true);
        slidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_off_width);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        slidingMenu.setMode(SlidingMenu.LEFT);
    }

    @Override
    public void toggle(View view) {
        slidingMenu.toggle();
    }

}
