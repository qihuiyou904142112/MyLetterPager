package com.qhy.letter.application;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * Created by qihuiyou on 2017/5/31.
 */

public class LetterApplicaiton extends Application {




    @Override
    public void onCreate() {
        super.onCreate();
        initXutils();
    }




    /**
     * Xutils 3
     */
    private void initXutils() {
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}
