package com.qhy.letter.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.qhy.letter.R;

/**
 * Created by qihuiyou on 2017/6/14.
 */

public class HomeGirdViewAdapter extends BaseAdapter{

    private Context mContext;

    public HomeGirdViewAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return View.inflate(mContext, R.layout.item_home_gridview,null);
    }
}
