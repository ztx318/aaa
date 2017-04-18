package com.example.administrator.nativebar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    private List<Fragment> fragments = new ArrayList<>();
    private BottomNavigationBar bnb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {

        bnb = (BottomNavigationBar) findViewById(R.id.bnb);
        bnb.setMode(BottomNavigationBar.MODE_SHIFTING);
        bnb.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);//默认风格，当然还有自定义风格
        bnb.addItem(new BottomNavigationItem(R.mipmap.t01d49b872004aca737, "首页")).setActiveColor(R.color.colorPrimary);
        bnb.addItem(new BottomNavigationItem(R.mipmap.t01d49b872004aca737, "发现")).setActiveColor(R.color.colorPrimary);
        bnb.addItem(new BottomNavigationItem(R.mipmap.t01d49b872004aca737, "我的")).setActiveColor(R.color.colorPrimary);

        bnb.setFirstSelectedPosition(0);
        bnb.initialise();

        fragments.add(new HomeFragment());
        fragments.add(new FindFragment());
        fragments.add(new MyFragment());

        getSupportFragmentManager().beginTransaction().replace(R.id.f1, fragments.get(0)).commit();

        bnb.setTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.f1, fragments.get(position)).commit();

            }
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
