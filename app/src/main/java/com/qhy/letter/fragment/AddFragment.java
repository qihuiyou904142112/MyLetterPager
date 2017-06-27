package com.qhy.letter.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qhy.letter.R;
import com.qhy.letter.base.CommonBaseFragment;
import com.qhy.letter.view.GifView;
import com.qhy.letter.view.SlidingMenu;

import java.util.ArrayList;

/**
 * Created by qihuiyou on 2017/6/2.
 *
 * 添加信笺
 *
 */

public class AddFragment extends CommonBaseFragment {

    private ImageView mLeftImage;
    private SlidingMenu mSlidingMenu;
    private GifView mMenuBg;
//    private NoScrollListview mMenuListView;
    private ArrayList<String> menuList = new ArrayList<>();


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_add, null);
        mLeftImage = (ImageView) inflate.findViewById(R.id.left_image);
//        mMenuListView = (NoScrollListview) inflate.findViewById(R.id.homr_add_menu_listview);
        mSlidingMenu = (SlidingMenu) inflate.findViewById(R.id.sliding_menu);
        mMenuBg = (GifView) inflate.findViewById(R.id.add_gf_menu);

        return inflate;
    }



    @Override
    protected void initData() {
        mBaseTitle.setText(R.string.tab_home_add);

        initMenuData();

    }

    private void initMenuData() {

        mLeftImage.setImageResource(R.mipmap.home_main_menu);

        mBaseLeftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlidingMenu.openMenu();
            }
        });


        mMenuBg.setMovieResource(R.drawable.timg);
        //待处理
//        mMenuListView.setAdapter(new AddMenuListAdapter(mContext,menuList,R.layout.menu_item_add));
    }






}
