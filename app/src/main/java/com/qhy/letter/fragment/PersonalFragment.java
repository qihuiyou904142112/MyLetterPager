package com.qhy.letter.fragment;

import android.view.LayoutInflater;
import android.view.View;

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
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_personal, null);

        return inflate;
    }

    @Override
    protected void initData() {

    }
}