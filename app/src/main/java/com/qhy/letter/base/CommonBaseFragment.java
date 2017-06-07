package com.qhy.letter.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qhy.letter.R;
import com.qhy.letter.activity.MainActivity;
import com.qhy.letter.utils.CommonUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by qihuiyou on 2017/5/9.
 * <p>
 * fragment基类
 */

public abstract class CommonBaseFragment extends Fragment {


    /**可以用到日志tag、请求tag*/
    protected final String TAG = getClass().getSimpleName();
    private View view;
    protected TextView mBaseTitle;
    protected RelativeLayout mBaseLeftBack;
    protected MainActivity mContext;
    private TextView statusView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = (MainActivity)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // 沉浸式状态栏
//        setTranslucentStatus();


        view = initView(inflater);
        //---------------标题栏----------------
        mBaseTitle = (TextView) view.findViewById(R.id.base_tv_title);
        mBaseLeftBack = (RelativeLayout) view.findViewById(R.id.base_left_back);


        setStatusHeight();


        return view;
    }

    /**
     * 设置系统状态栏高度
     */
    private void setStatusHeight() {
        View topView = View.inflate(mContext,R.layout.top_title_bar,null);
        statusView = (TextView) topView.findViewById(R.id.view_status_height);
        int statusHeight = CommonUtil.getStatusHeight(mContext);
        ViewGroup.LayoutParams params = statusView.getLayoutParams();
        params.height = statusHeight;
        statusView.setLayoutParams(params);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 初始化view
     */
    protected  abstract View initView(LayoutInflater inflater);

    /**
     * 初始化数据
     */
    protected  abstract void initData();

    protected void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(getActivity());
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.drawable.bg_title_bar);// 通知栏所需颜色
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getActivity().getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 获取状态栏高度
     *
     * @param view
     */
    public void setStatusHeight(View view) {
        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int barHeight = 0;
        if (resId > 0) {
            barHeight = getResources().getDimensionPixelSize(resId);
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = barHeight;

    }


}
