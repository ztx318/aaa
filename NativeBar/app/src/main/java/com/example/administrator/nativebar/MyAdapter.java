package com.example.administrator.nativebar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2017/4/12.
 */

public class MyAdapter extends FragmentPagerAdapter {
    private String[] Titles = new String[]{"A", "B", "C"};
    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new Fragment2();
            case 2:
                return new Fragment3();
        }
        return new Fragment1();
    }

    @Override
    public int getCount() {
        return Titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }
}
