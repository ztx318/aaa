package com.itheima.pcddemo15;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by xx on 2016/9/20.
 */
public class AreaAdapter extends BaseAdapter {
    private  List<AreaBean> mDatas;
    private  Context mContext;

    public AreaAdapter(Context context, List<AreaBean> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public AreaBean getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
//            convertView = View.inflate(mContext, R.layout.item_area, null);//该方法会导致布局文件的第一层控件的布局参数失效
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_area, parent, false);//该方法会导致布局文件的第一层控件的布局参数有效

        }

        AreaBean areaBean = getItem(position);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        tv_name.setText(areaBean.name);
        if (areaBean.isSelected) {
            tv_name.setBackgroundResource(R.drawable.choose_item_selected);
        } else {
            tv_name.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
        }
        return convertView;
    }
}
