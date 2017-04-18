package com.example.administrator.nativebar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/12 0012.
 */

public class HomeFragment extends Fragment {
    private TabLayout ta;
    private ViewPager vp;
    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_homefragment,container,false);
        ta = (TabLayout) view.findViewById(R.id.tab);

        vp = (ViewPager) view.findViewById(R.id.vp);
        vp.setAdapter(new MyAdapter(getChildFragmentManager()));
        ta.setupWithViewPager(vp);

        one = ta.getTabAt(0);
        two = ta.getTabAt(1);
        three = ta.getTabAt(2);
        one.setIcon(R.mipmap.ic_launcher);
        two.setIcon(R.mipmap.ic_launcher);
        three.setIcon(R.mipmap.ic_launcher);
        ta.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab t) {
                if (t == one) {
                    one.setIcon(R.mipmap.t01d49b872004aca737);
                    vp.setCurrentItem(0);
                }
                if (t == two) {
                    two.setIcon(R.mipmap.t01d49b872004aca737);
                    vp.setCurrentItem(1);
                }
                if (t == three) {
                    three.setIcon(R.mipmap.t01d49b872004aca737);
                    vp.setCurrentItem(2);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab t) {
                if (t == one) {
                    one.setIcon(R.mipmap.ic_launcher);
                }
                if (t == two) {
                    two.setIcon(R.mipmap.ic_launcher);
                }
                if (t == three) {
                    three.setIcon(R.mipmap.ic_launcher);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab t) {

            }
        });

        return view;
    }
}
