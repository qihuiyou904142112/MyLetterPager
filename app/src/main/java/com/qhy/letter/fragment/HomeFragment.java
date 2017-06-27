package com.qhy.letter.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.qhy.letter.R;
import com.qhy.letter.adapter.HomeGirdViewAdapter;
import com.qhy.letter.base.CommonBaseFragment;
import com.qhy.letter.utils.T;
import com.qhy.letter.view.GifView;
import com.qhy.letter.view.GradationScrollView;
import com.qhy.letter.view.MyGridView;
import com.qhy.letter.view.UpDownViewSwitcher;
import com.qhy.letter.view.convenientbanner.CBLoopViewPager;
import com.qhy.letter.view.convenientbanner.ConvenientBanner;
import com.qhy.letter.view.convenientbanner.holder.CBViewHolderCreator;
import com.qhy.letter.view.convenientbanner.holder.LocalImageHolderView;
import com.qhy.letter.view.convenientbanner.listener.OnItemClickListener;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by qihuiyou on 2017/6/2.
 * <p>
 * 首页
 */

public class HomeFragment extends CommonBaseFragment implements OnItemClickListener {

    private GradationScrollView mScrollView;
    private ListView mListView;

    private ConvenientBanner convenientBanner;
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    private LinearLayout mHome_ll_title;
    private CBLoopViewPager mViewPager;
    private UpDownViewSwitcher mViewSwitcher;
    private GifView mGf_wang, mGf_zhe, mGf_rong, mGf_yao;
    private MyGridView myGridView;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_home, null);
        mScrollView = (GradationScrollView) inflate.findViewById(R.id.scrollview);
        mListView = (ListView) inflate.findViewById(R.id.listview);
        mHome_ll_title = (LinearLayout) inflate.findViewById(R.id.home_ll_title);

        convenientBanner = (ConvenientBanner) inflate.findViewById(R.id.convenientBanner);

        mViewSwitcher = (UpDownViewSwitcher) inflate.findViewById(R.id.home_view_switcher);


        mGf_wang = (GifView) inflate.findViewById(R.id.gf_wang);
        mGf_zhe = (GifView) inflate.findViewById(R.id.gf_zhe);
        mGf_rong = (GifView) inflate.findViewById(R.id.gf_rong);
        mGf_yao = (GifView) inflate.findViewById(R.id.gf_yao);

        myGridView = (MyGridView) inflate.findViewById(R.id.home_gr);


        return inflate;
    }

    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(3000);
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }

    @Override
    protected void initData() {

        myGridView.setAdapter(new HomeGirdViewAdapter(mContext));

        setHomeGifViewData();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.data));
        mViewPager = convenientBanner.getViewPager();
        mListView.setAdapter(adapter);
        initBanner();
        initListeners();
        setTransformer();
        setSwitvher();
    }

    /**
     * 设置GIF动态图片资源
     */
    private void setHomeGifViewData() {

        mGf_wang.setMovieResource(R.drawable.wang);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mGf_wang.setPaused(true);
//
//
//            }
//        },3000);

        mGf_zhe.setMovieResource(R.drawable.zhe);
        mGf_rong.setMovieResource(R.drawable.rong);
        mGf_yao.setMovieResource(R.drawable.yao);

    }




    private void initBanner() {

        //本地图片集合
        for (int position = 0; position < 7; position++) {
            localImages.add(getResId("ic_test_" + position, R.drawable.class));
        }


        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                .setOnItemClickListener(this);


    }

    /**
     * 设置轮播条目
     */
    private void setSwitvher() {

        mViewSwitcher.setSwitcheNextViewListener(new UpDownViewSwitcher.SwitchNextViewListener() {
            @Override
            public void switchTONextView(View nextView, int index) {
                if (nextView == null) return;
                ((TextView) nextView.findViewById(R.id.textview)).setText("测试内容是法国的风格的风格的风格的风格:" + index);

                ((TextView) nextView.findViewById(R.id.switch_title_text)).setText("测试title:");
                nextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T.showShort(mContext, "待完成");
                    }
                });
            }
        });
        mViewSwitcher.setContentLayout(R.layout.switch_view);

    }

    /**
     * 设置banner轮播动画
     */
    private void setTransformer() {

        String transforemerName = AccordionTransformer.class.getSimpleName();
        try {
            Class cls = Class.forName("com.ToxicBakery.viewpager.transforms." + transforemerName);
            ABaseTransformer transforemer = (ABaseTransformer) cls.newInstance();
            mViewPager.setPageTransformer(true, transforemer);

            if (transforemerName.equals("StackTransformer")) {
                convenientBanner.setScrollDuration(1200);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     *
     * @param variableName
     * @param c
     * @return
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    /**
     * 获取顶部图片高度
     */
    private void initListeners() {

        ViewTreeObserver vto = mViewPager.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setGradationListener(mViewPager.getHeight());
                mViewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

    }

    /**
     * 设置动态监听
     *
     * @param imageHeight
     */
    private void setGradationListener(final int imageHeight) {

        mScrollView.setScrollViewListener(new GradationScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {
                if (y <= 0) {   //设置标题的背景颜色
                    mHome_ll_title.setBackgroundColor(Color.argb((int) 0, 62, 144, 227));
                } else if (y > 0 && y <= imageHeight) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                    float scale = (float) y / imageHeight;
                    float alpha = (255 * scale);
                    mHome_ll_title.setBackgroundColor(Color.argb((int) alpha, 62, 144, 227));
                } else {    //滑动到banner下面设置普通颜色

                    mHome_ll_title.setBackgroundColor(Color.argb((int) 255, 62, 144, 227));

                    mHome_ll_title.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
            }
        });
    }


    @Override
    public void onItemClick(int position) {
        Toast.makeText(mContext, "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
    }


}
