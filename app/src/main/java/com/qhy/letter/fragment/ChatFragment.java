package com.qhy.letter.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qhy.letter.R;
import com.qhy.letter.base.CommonBaseFragment;
import com.qhy.letter.utils.Logs;

/**
 * Created by qihuiyou on 2017/6/2.
 * <p>
 * 消息
 */

public class ChatFragment extends CommonBaseFragment{



    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_chat, null);


        inflate.findViewById(R.id.demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logs.d(TAG,"正在获取中...");
                getLocationData();
            }
        });

        return inflate;
    }

    @Override
    protected void initData() {
        mBaseTitle.setText("情绪聊天");
        mBaseLeftBack.setVisibility(View.INVISIBLE);


    }

    private void getLocationData() {








    }


}