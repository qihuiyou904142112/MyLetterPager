package com.qhy.letter.activity;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qhy.letter.R;
import com.qhy.letter.fragment.AddFragment;
import com.qhy.letter.fragment.ChatFragment;
import com.qhy.letter.fragment.HomeFragment;
import com.qhy.letter.fragment.LifeFragment;
import com.qhy.letter.fragment.PersonalFragment;
import com.qhy.letter.utils.CommonUtil;
import com.qhy.letter.utils.T;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private long exitTime = 0;
    private TextView mMain_home, mMain_chat, mMain_life, mMain_personal;
    private ImageView mMain_add;
    private ArrayList<Fragment> mFsList = new ArrayList<>();


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
        mMain_home = (TextView) findViewById(R.id.main_home);
        mMain_chat = (TextView) findViewById(R.id.main_chat);
        mMain_add = (ImageView) findViewById(R.id.main_add);
        mMain_life = (TextView) findViewById(R.id.main_life);
        mMain_personal = (TextView) findViewById(R.id.main_personal);

    }


    private void initListener() {
        mMain_home.setOnClickListener(this);
        mMain_chat.setOnClickListener(this);
        mMain_add.setOnClickListener(this);
        mMain_life.setOnClickListener(this);
        mMain_personal.setOnClickListener(this);
    }

    private void initTabFragment() {
        mFsList.add(new HomeFragment());
        mFsList.add(new LifeFragment());
        mFsList.add(new AddFragment());
        mFsList.add(new ChatFragment());
        mFsList.add(new PersonalFragment());

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


    /**
     * 导航栏TOP图标
     *
     * @param id
     * @param view
     */
    private void style(TextView view,int id) {
        view.setCompoundDrawablesRelativeWithIntrinsicBounds(null, ContextCompat.getDrawable(this, id), null, null);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_home:

                changeFrag(0);

                style(mMain_home,R.mipmap.tab_news_selected);
                style(mMain_life,R.mipmap.tab_find_normal);
                style(mMain_chat,R.mipmap.tab_battle_normal);
                style(mMain_personal,R.mipmap.tab_auxiliary_normal);

                break;
            case R.id.main_life:

                changeFrag(1);


                style(mMain_home,R.mipmap.tab_news_normal);
                style(mMain_life,R.mipmap.tab_find_selected);
                style(mMain_chat,R.mipmap.tab_battle_normal);
                style(mMain_personal,R.mipmap.tab_auxiliary_normal);

                break;
            case R.id.main_add:

                changeFrag(2);


                style(mMain_home,R.mipmap.tab_news_normal);
                style(mMain_life,R.mipmap.tab_find_normal);
                style(mMain_chat,R.mipmap.tab_battle_normal);
                style(mMain_personal,R.mipmap.tab_auxiliary_normal);

                break;
            case R.id.main_chat:

                changeFrag(3);

                style(mMain_home,R.mipmap.tab_news_normal);
                style(mMain_life,R.mipmap.tab_find_normal);
                style(mMain_chat,R.mipmap.tab_battle_selected);
                style(mMain_personal,R.mipmap.tab_auxiliary_normal);

                break;
            case R.id.main_personal:

                changeFrag(4);

                style(mMain_home,R.mipmap.tab_news_normal);
                style(mMain_life,R.mipmap.tab_find_normal);
                style(mMain_chat,R.mipmap.tab_battle_normal);
                style(mMain_personal,R.mipmap.tab_auxiliary_selected);

                break;
        }
    }
}
