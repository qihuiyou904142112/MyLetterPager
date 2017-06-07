package com.qhy.letter.activity;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.qhy.letter.R;
import com.qhy.letter.fragment.AddFragment;
import com.qhy.letter.fragment.ChatFragment;
import com.qhy.letter.fragment.HomeFragment;
import com.qhy.letter.fragment.LifeFragment;
import com.qhy.letter.fragment.PersonalFragment;
import com.qhy.letter.utils.CommonUtil;
import com.qhy.letter.utils.T;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {

    private long exitTime = 0;
    private RadioButton mMain_home, mMain_chat, mMain_add, mMain_life, mMain_personal;
    private static final String TAG = "MainActivity";

    private ArrayList<Fragment> mFsList = new ArrayList<>();
    private RadioGroup mRg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_main);

        initView();
        initTabFragment();
        initListener();


    }


    private void initView() {
        mMain_home = (RadioButton) findViewById(R.id.main_home);
        mMain_chat = (RadioButton) findViewById(R.id.main_chat);
        mMain_add = (RadioButton) findViewById(R.id.main_add);
        mMain_life = (RadioButton) findViewById(R.id.main_life);
        mMain_personal = (RadioButton) findViewById(R.id.main_personal);
        mRg = (RadioGroup) findViewById(R.id.rg_title);

    }


    private void initListener() {
        mRg.setOnCheckedChangeListener(this);
    }

    private void initTabFragment() {
        mFsList.add(new HomeFragment());
        mFsList.add(new LifeFragment());
        mFsList.add(new AddFragment());
        mFsList.add(new ChatFragment());
        mFsList.add(new PersonalFragment());

        mRg.check(R.id.main_home); //默认选中首页
        changeFrag(0);

    }

    private void changeFrag(int curIndex) {
        for (int i = 0; i < mFsList.size(); i++) {
            if (i == curIndex) {
                showFragment(mFsList.get(i));
            } else {
                hideFragment(mFsList.get(i));
            }
        }
    }


    protected void hideFragment(Fragment currFragment) {
        if (currFragment == null)
            return;
        FragmentTransaction currFragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        currFragment.onPause();
        if (currFragment.isAdded()) {
            currFragmentTransaction.hide(currFragment);
            currFragmentTransaction.commitAllowingStateLoss();
        }
    }

    protected void showFragment(Fragment startFragment) {
        if (startFragment == null)
            return;
        FragmentTransaction startFragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        if (!startFragment.isAdded()) {
            startFragmentTransaction.add(R.id.fl_main_content, startFragment);
        } else {
            startFragment.onResume();
            startFragmentTransaction.show(startFragment);
        }
        startFragmentTransaction.commitAllowingStateLoss();
    }


    @Override
    public void onBackPressed() {

        if ((System.currentTimeMillis() - exitTime) > 3000) {
            T.showLong(this, "再按一次退出信笺");
            exitTime = System.currentTimeMillis();
            return;
        }
        CommonUtil.exitApp(MainActivity.this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.main_home:
                changeFrag(0);
                break;
            case R.id.main_life:
                changeFrag(1);
                break;
            case R.id.main_add:
                changeFrag(2);
                break;
            case R.id.main_chat:
                changeFrag(3);
                break;
            case R.id.main_personal:
                changeFrag(4);
                break;
        }
    }
}
