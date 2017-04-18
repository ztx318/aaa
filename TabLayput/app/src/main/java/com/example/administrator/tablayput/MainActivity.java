package com.example.administrator.tablayput;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabout;
    private ViewPager viewPager;
    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    private TabLayout.Tab four;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabout = (TabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.vp);

        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
       // tabLayout绑定viewpager
        tabout.setupWithViewPager(viewPager);

        one = tabout.getTabAt(0);
        two = tabout.getTabAt(1);
        three = tabout.getTabAt(2);
        four = tabout.getTabAt(3);

        one.setIcon(R.mipmap.ic_launcher);
        two.setIcon(R.mipmap.ic_launcher);
        three.setIcon(R.mipmap.ic_launcher);
        four.setText("书架");


        tabout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab == tabout.getTabAt(0)) {
                    one.setIcon(R.mipmap.car);
                    viewPager.setCurrentItem(0);
                } else if (tab == tabout.getTabAt(1)) {
                    two.setIcon(R.mipmap.car);
                    viewPager.setCurrentItem(1);
                } else if (tab == tabout.getTabAt(2)) {
                    three.setIcon(R.mipmap.car);
                    viewPager.setCurrentItem(2);
                }else if (tab == tabout.getTabAt(3)) {
                    four.setText("书架");
                    viewPager.setCurrentItem(3);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab == tabout.getTabAt(0)) {
                    one.setIcon(R.mipmap.ic_launcher);
                } else if (tab == tabout.getTabAt(1)) {
                    two.setIcon(R.mipmap.ic_launcher);
                } else if (tab == tabout.getTabAt(2)) {
                    three.setIcon(R.mipmap.ic_launcher);
                } else if (tab == tabout.getTabAt(3)) {
                    four.setText("书架");
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
   }
}
