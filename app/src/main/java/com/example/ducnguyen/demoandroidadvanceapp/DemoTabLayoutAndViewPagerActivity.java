package com.example.ducnguyen.demoandroidadvanceapp;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DemoTabLayoutAndViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_tab_layout_and_view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout_main);
        ViewPager viewPager = findViewById(R.id.view_pager_main);
        DemoViewPagerAdapter demoViewPagerAdapter
                = new DemoViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(demoViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
