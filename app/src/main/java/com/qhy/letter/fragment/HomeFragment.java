package com.qhy.letter.fragment;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.qhy.letter.R;
import com.qhy.letter.base.CommonBaseFragment;
import com.qhy.letter.view.GradationScrollView;
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
//    private ViewPager mViewPager;
//    private MaterialIndicator mIndicator;

    private ConvenientBanner convenientBanner;
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    private LinearLayout mHome_ll_title;
    private CBLoopViewPager mViewPager;


    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_home, null);
        mScrollView = (GradationScrollView) inflate.findViewById(R.id.scrollview);
        mListView = (ListView) inflate.findViewById(R.id.listview);
        mHome_ll_title = (LinearLayout) inflate.findViewById(R.id.home_ll_title);
//        mViewPager = (ViewPager) inflate.findViewById(R.id.viewPager);
//        mIndicator = (MaterialIndicator) inflate.findViewById(R.id.indicator);

        convenientBanner = (ConvenientBanner) inflate.findViewById(R.id.convenientBanner);


        return inflate;
    }

    @Override
    protected void initData() {


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.data));
        mListView.setAdapter(adapter);
        initBanner();
        initListeners();
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

    private void initBanner() {

        //本地图片集合
        for (int position = 0; position < 7; position++)
            localImages.add(getResId("ic_test_" + position, R.drawable.class));

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

        mViewPager = convenientBanner.getViewPager();

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

    @Override
    public void onItemClick(int position) {
        Toast.makeText(mContext, "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
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
                }
            }
        });
    }


}
