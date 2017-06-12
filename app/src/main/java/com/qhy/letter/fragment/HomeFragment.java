package com.qhy.letter.fragment;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.qhy.letter.R;
import com.qhy.letter.base.CommonBaseFragment;
import com.qhy.letter.view.GradationScrollView;
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
    private TextView textView;
//    private ViewPager mViewPager;
//    private MaterialIndicator mIndicator;

    private ConvenientBanner convenientBanner;
    private ArrayList<Integer> localImages = new ArrayList<Integer>();


    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_home, null);
        mScrollView = (GradationScrollView) inflate.findViewById(R.id.scrollview);
        mListView = (ListView) inflate.findViewById(R.id.listview);
        textView = (TextView) inflate.findViewById(R.id.textview);
//        mViewPager = (ViewPager) inflate.findViewById(R.id.viewPager);
//        mIndicator = (MaterialIndicator) inflate.findViewById(R.id.indicator);

        convenientBanner = (ConvenientBanner) inflate.findViewById(R.id.convenientBanner);


        return inflate;
    }

    @Override
    protected void initData() {

//        mViewPager.setFocusable(true);
//        mViewPager.setFocusableInTouchMode(true);
//        mViewPager.requestFocus();
//        mViewPager.setAdapter(new MyPagerAdapter());
//        mViewPager.addOnPageChangeListener(mIndicator);
//        mIndicator.setAdapter(mViewPager.getAdapter());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.data));
        mListView.setAdapter(adapter);

        initListeners();

        initBanner();
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
                //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnPageChangeListener(this)//监听翻页事件
                .setOnItemClickListener(this);

        String transforemerName = AccordionTransformer.class.getSimpleName();
        try {
            Class cls = Class.forName("com.ToxicBakery.viewpager.transforms." + transforemerName);
            ABaseTransformer transforemer = (ABaseTransformer) cls.newInstance();
            convenientBanner.getViewPager().setPageTransformer(true, transforemer);

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
        Toast.makeText(mContext,"点击了第"+position+"个",Toast.LENGTH_SHORT).show();
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
     * viewpager适配器
     */
    private class MyPagerAdapter extends PagerAdapter {
        public int[] drawables = {R.mipmap.banner1
                , R.mipmap.banner2, R.mipmap.banner3, R.mipmap.banner4};

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object == view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(drawables[position]);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(((View) object));
        }
    }

    /**
     * 获取顶部图片高度
     */
    private void initListeners() {

        mScrollView.setScrollViewListener(new GradationScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {
                if (y <= 0) {   //设置标题的背景颜色
                    textView.setBackgroundColor(Color.argb((int) 0, 62, 144, 227));
                } else if (y > 0 && y <= 400) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                    float scale = (float) y / 400;
                    float alpha = (255 * scale);
                    textView.setTextColor(Color.argb((int) alpha, 255, 255, 255));
                    textView.setBackgroundColor(Color.argb((int) alpha, 62, 144, 227));
                } else {    //滑动到banner下面设置普通颜色
                    textView.setBackgroundColor(Color.argb((int) 255, 62, 144, 227));
                }
            }
        });

//        ViewTreeObserver vto = mViewPager.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
////                setGradationListener(mViewPager.getHeight());
////                mViewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//            }
//        });


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
                    textView.setBackgroundColor(Color.argb((int) 0, 144, 151, 166));
                } else if (y > 0 && y <= imageHeight) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                    float scale = (float) y / imageHeight;
                    float alpha = (255 * scale);
                    textView.setTextColor(Color.argb((int) alpha, 255, 255, 255));
                    textView.setBackgroundColor(Color.argb((int) alpha, 144, 151, 166));
                } else {    //滑动到banner下面设置普通颜色
                    textView.setBackgroundColor(Color.argb((int) 255, 144, 151, 166));
                }
            }
        });
    }


}
