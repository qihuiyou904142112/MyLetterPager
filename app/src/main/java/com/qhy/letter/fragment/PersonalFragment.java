package com.qhy.letter.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qhy.letter.R;
import com.qhy.letter.base.CommonBaseFragment;

/**
 * Created by qihuiyou on 2017/6/2.
 *
 * 我的
 *
 */

public class PersonalFragment extends CommonBaseFragment {
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_personal, null);

        return inflate;
    }

    @Override
    protected void initData() {

    }
}
