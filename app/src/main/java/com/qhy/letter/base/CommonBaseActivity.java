package com.qhy.letter.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.qhy.letter.R;
import com.qhy.letter.view.ShapeLoadingDialog;
import com.readystatesoftware.systembartint.SystemBarTintManager;


/**
 * Created by qihuiyou on 2017/5/9.
 * <p>
 * activity基类
 */

public abstract class CommonBaseActivity extends FragmentActivity {

    protected ShapeLoadingDialog mLoadingDialog;
    /**可以用到日志tag、请求tag*/
    protected final String TAG = getClass().getSimpleName();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //防止hide()与show()导致的Fragment重叠
        if (savedInstanceState != null) {
            String FRAGMENTS_TAG = "android:support:fragments";
            savedInstanceState.remove(FRAGMENTS_TAG);
        }


        super.onCreate(savedInstanceState);

        // 沉浸式状态栏
        setTranslucentStatus();
        initView(savedInstanceState);
        initData();
        initLoadingView();
    }


    /**
     * 初始化标题栏
     *
     * @param title 标题名
     */
    protected void initHeader(String title) {
        ((TextView) findViewById(R.id.base_tv_title)).setText(title);
        findViewById(R.id.base_left_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 加载动画
     */
    private void initLoadingView() {
        mLoadingDialog = new ShapeLoadingDialog(this);
    }


    protected void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.drawable.bg_title_bar);// 通知栏所需颜色
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


}
