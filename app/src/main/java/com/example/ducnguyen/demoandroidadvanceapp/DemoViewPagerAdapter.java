package com.example.ducnguyen.demoandroidadvanceapp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class DemoViewPagerAdapter extends FragmentPagerAdapter {
    private static String TITLE = "TAB ";
    private static int NUMBER_TABS = 3;

    public DemoViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TabOneFragment();
            case 1:
                return new TabTwoFragment();
            case 2:
                return new TabThreeFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUMBER_TABS;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLE + position;
    }
}
