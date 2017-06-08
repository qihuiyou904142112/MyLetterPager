package com.qhy.letter.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.qhy.letter.R;
import com.qhy.letter.base.CommonBaseFragment;

/**
 * Created by qihuiyou on 2017/6/2.
 *
 * 添加信笺
 *
 */

public class AddFragment extends CommonBaseFragment {
    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_add, null);

        return inflate;
    }

    @Override
    protected void initData() {
        mBaseTitle.setText("情绪信笺");
        mBaseLeftBack.setVisibility(View.INVISIBLE);
    }
}
