package com.qhy.letter.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qhy.letter.R;
import com.qhy.letter.utils.CommonUtil;

/**
 * Created by qihuiyou on 2017/5/31.
 */

public class SplashActivity extends Activity {

    private TextView mTime;
    private int NUMBER = 5000;//倒计时
    private LinearLayout mIntoMain;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }


    private void initView() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_splash);

        mTime = (TextView) findViewById(R.id.splash_time);
        mIntoMain = (LinearLayout) findViewById(R.id.ll_into_main);

    }

    private void initData() {
        //倒计时
        countDownTimer = new CountDownTimer(NUMBER, 1000) {
             @Override
             public void onTick(long millisUntilFinished) {
                 mTime.setText(String.valueOf(millisUntilFinished/1000-1));
                 if(millisUntilFinished/1000 == 1){
                     loadMainUI();
                 }
             }

             @Override
             public void onFinish() {

             }
         };

        countDownTimer.start();


        mIntoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.startActivity(SplashActivity.this,
                        MainActivity.class);
                countDownTimer.cancel();
                finish();
            }
        });

    }

    /**
     * 进入主界面
     */
    private void loadMainUI() {
        CommonUtil.startActivity(SplashActivity.this,
                MainActivity.class);
        overridePendingTransition(R.anim.fade_center_in,R.anim.fade_center_out);
        countDownTimer.cancel();
        finish();
    }

}
