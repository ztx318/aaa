package com.itheima.pcddemo15;

import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText mEditText;
    private Button mButton;
    private PopupWindow mPw;
    private ListView mLv_province;//省份
    private ListView mLv_city;//城市
    private ListView mLv_district;//地区
    private List<AreaBean> mProvinceLists = new ArrayList<>();//省份的数据集合
    private List<AreaBean> mCityLists = new ArrayList<>();//城市的数据集合
    private List<AreaBean> mDistrictLists = new ArrayList<>();//地区的数据集合
    private File mDbFile;
    private AreaAdapter mProvinceAdapter;

    private int prevousProvincePosition = -1;//记录当前省份的前一个选中位置
    private int prevousCityPosition = -1;//记录当前省份的前一个选中位置
    private int prevousDistrictPosition = -1;//记录当前省份的前一个选中位置
    private AreaAdapter mCityAdapter;
    private AreaAdapter mDistrictAdapter;

    private String province = "";
    private String city = "";
    private String district = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {

        //数据库的复制
        try {
            initDb();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //创建popupwindow
        initPopUpWindow();

        //初始化省份的数据，为之后直接显示做准备
        initProviceListView();

        //给三个listview设置条目点击事件
        initListViewListener();
    }

    private void initListViewListener() {
        mLv_province.setOnItemClickListener(this);
        mLv_city.setOnItemClickListener(this);
        mLv_district.setOnItemClickListener(this);
    }

    private void initProviceListView() {
        readProvinceLists();

        if (mProvinceAdapter == null) {
            mProvinceAdapter = new AreaAdapter(MainActivity.this, mProvinceLists);
            mLv_province.setAdapter(mProvinceAdapter);
        } else {
            mProvinceAdapter.notifyDataSetChanged();
        }
    }

    //读取省份数据
    private void readProvinceLists() {
        //select code , name from province;
        //从数据库中获取省份的数据，并封装到数据集合中‘
        SQLiteDatabase database = SQLiteDatabase.openDatabase(mDbFile.getAbsolutePath(), null, SQLiteDatabase
                .OPEN_READWRITE);
        Cursor cursor = database.rawQuery("select code , name from province", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String code = cursor.getString(0);
                //gbk ->byte[]->utf-8->乱码
//                String name = cursor.getString(1);
                byte[] blob = cursor.getBlob(1);
                String name = null;
                try {
                    name = new String(blob,"gbk");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                AreaBean areaBean = new AreaBean();
                areaBean.code = code;
                areaBean.name = name;
                mProvinceLists.add(areaBean);
            }
        }
        cursor.close();
        database.close();
    }

    private void initPopUpWindow() {

        if (mPw == null) {
            View contentView = View.inflate(MainActivity.this,R.layout.view_pop,null);
            mPw = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
                    .WRAP_CONTENT, true);
            mPw.setBackgroundDrawable(new ColorDrawable());
            mLv_province = (ListView) contentView.findViewById(R.id.lv_province);
            mLv_city = (ListView) contentView.findViewById(R.id.lv_city);
            mLv_district = (ListView) contentView.findViewById(R.id.lv_district);
        }
    }

    private void initDb() throws Exception {

        mDbFile = new File(getFilesDir(),"city.s3db");

        if (mDbFile.exists()) {//如果文件存在就进行复制操作
            return;
        }
        //方式一：res/raw
//        InputStream inputStream = getResources().openRawResource(R.raw.city);
        //方式二：assets
        AssetManager assetManager = getAssets();
        InputStream inputStream = assetManager.open("city.s3db");

        FileOutputStream fos = new FileOutputStream(mDbFile);

        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inputStream.read(buffer)) != -1) {
            fos.write(buffer,0,len);
        }

        inputStream.close();
        fos.close();
    }

    private void initView() {
        mEditText = (EditText) findViewById(R.id.et_number);
        mButton = (Button) findViewById(R.id.bt);

        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
                mPw.showAsDropDown(mButton);
                break;
        }
    }

    /**
     *
     * @param parent    被点击的listview
     * @param view      被点击的条目对象
     * @param position  点击的位置
     * @param id        适配器中返回的id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lv_province:
                //先将上一个选中位置设置为未选中
                if (prevousProvincePosition != -1&&prevousProvincePosition != position) {
                    mProvinceLists.get(prevousProvincePosition).isSelected = false;
                }
                //记录当前位置为下一次移动做准备
                prevousProvincePosition = position;
//                view.setBackgroundResource(R.drawable.choose_item_selected);
                //获取条目数据，设置为选中状态，刷新界面
                AreaBean provinceBean = (AreaBean) parent.getItemAtPosition(position);
                if (!provinceBean.isSelected) {
                    provinceBean.isSelected = true;
                    mProvinceAdapter.notifyDataSetChanged();
                }

                //根据点击的省份显示对应的城市
                initCityListViewByProvice(provinceBean);
                //点击省份，必须将城市的前一个选中的位置初始化掉
                prevousCityPosition = -1;

                //让地区不显示
                if (mDistrictAdapter != null) {
                    mDistrictLists.clear();
                    mDistrictAdapter.notifyDataSetChanged();
                }

                //记录当前点击的省份的名称
                province = provinceBean.name;
                mEditText.setText(province);
                break;
            case R.id.lv_city:
                //先将上一个选中位置设置为未选中
                if (prevousCityPosition != -1&&prevousCityPosition != position) {
                    mCityLists.get(prevousCityPosition).isSelected = false;
                }
                //记录当前位置为下一次移动做准备
                prevousCityPosition = position;
//                view.setBackgroundResource(R.drawable.choose_item_selected);
                //获取条目数据，设置为选中状态，刷新界面
                AreaBean cityBean = (AreaBean) parent.getItemAtPosition(position);
                if (!cityBean.isSelected) {
                    cityBean.isSelected = true;
                    mCityAdapter.notifyDataSetChanged();
                }

                //点击城市，根据城市展示地区界面
                initDistrictListViewByCity(cityBean);

                //点击城市时，初始化地区的前一个选中位置
                prevousDistrictPosition = -1;

                //记录当前点击的省份的名称
                city = cityBean.name;
                mEditText.setText(province+" "+city);
                break;
            case R.id.lv_district:
                //先将上一个选中位置设置为未选中
                if (prevousDistrictPosition != -1&&prevousDistrictPosition != position) {
                    mDistrictLists.get(prevousDistrictPosition).isSelected = false;
                }
                //记录当前位置为下一次移动做准备
                prevousDistrictPosition = position;
//                view.setBackgroundResource(R.drawable.choose_item_selected);
                //获取条目数据，设置为选中状态，刷新界面
                AreaBean districtBean = (AreaBean) parent.getItemAtPosition(position);
                if (!districtBean.isSelected) {
                    districtBean.isSelected = true;
                    mDistrictAdapter.notifyDataSetChanged();
                }

                //记录当前点击的省份的名称
                district = districtBean.name;
                mEditText.setText(province+" "+city+" "+district);
                break;
        }
    }

    private void initDistrictListViewByCity(AreaBean cityBean) {
        //读取地区的数据
        readDistrictListsByCity(cityBean);
        
        
        if (mDistrictAdapter == null) {
            mDistrictAdapter = new AreaAdapter(MainActivity.this, mDistrictLists);
            mLv_district.setAdapter(mDistrictAdapter);
        }else{
            mDistrictAdapter.notifyDataSetChanged();
        }
    }

    private void readDistrictListsByCity(AreaBean cityBean) {
        readAreaByPcode(cityBean,mDistrictLists,"district");
    }

    //根据点击的省份显示对应的城市
    private void initCityListViewByProvice(AreaBean provinceBean) {
        //读取城市数据
        readCityListsByProvince(provinceBean);

        if (mCityAdapter == null) {
            mCityAdapter = new AreaAdapter(MainActivity.this, mCityLists);
            mLv_city.setAdapter(mCityAdapter);
        } else {
            mCityAdapter.notifyDataSetChanged();
        }
    }

    //读取城市数据
    private void readCityListsByProvince(AreaBean provinceBean) {
        readAreaByPcode(provinceBean, mCityLists, "city");
    }

    //根据传递进来的集合与表名，获取对应的数据
    private void readAreaByPcode(AreaBean provinceBean, List<AreaBean> lists, String table_name) {
        lists.clear();

        //select code , name from city where pcode = '310000';
        SQLiteDatabase database = SQLiteDatabase.openDatabase(mDbFile.getAbsolutePath(), null, SQLiteDatabase
                .OPEN_READWRITE);
        Cursor cursor = database.rawQuery("select code , name from "+table_name+" where pcode = ?", new String[]{provinceBean
                .code});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String code = cursor.getString(0);
                String name = "";
                try {
                    name  = new String(cursor.getBlob(1),"gbk");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                AreaBean areaBean = new AreaBean();
                areaBean.code = code;
                areaBean.name = name;
                lists.add(areaBean);
            }
        }
        cursor.close();
        database.close();
    }
}
