package com.qhy.letter.fragment;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qhy.letter.R;
import com.qhy.letter.base.CommonBaseFragment;
import com.qhy.letter.view.GradationTitle.GradationScrollView;
import com.qhy.letter.view.GradationTitle.MaterialIndicator;

/**
 * Created by qihuiyou on 2017/6/2.
 *
 * 首页
 *
 */

public class HomeFragment extends CommonBaseFragment {

    private GradationScrollView mScrollView;
    private ListView mListView;
    private TextView textView;
    private int imageHeight;
    private ViewPager mViewPager;
    private MaterialIndicator mIndicator;


    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_home, null);
        mScrollView = (GradationScrollView) inflate.findViewById(R.id.scrollview);
        mListView = (ListView) inflate.findViewById(R.id.listview);
        textView = (TextView) inflate.findViewById(R.id.textview);
        mViewPager = (ViewPager) inflate.findViewById(R.id.viewPager);
        mIndicator = (MaterialIndicator) inflate.findViewById(R.id.indicator);
        return inflate;
    }

    @Override
    protected void initData() {


        mViewPager.setFocusable(true);
        mViewPager.setFocusableInTouchMode(true);
        mViewPager.requestFocus();
        mViewPager.setAdapter(new MyPagerAdapter());
        mViewPager.addOnPageChangeListener(mIndicator);
        mIndicator.setAdapter(mViewPager.getAdapter());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.data));
        mListView.setAdapter(adapter);

        initListeners();
    }

    /**
     * viewpager适配器
     */
    private  class MyPagerAdapter extends PagerAdapter {
        public int[] drawables = {R.mipmap.banner1
                ,R.mipmap.banner2,R.mipmap.banner3,R.mipmap.banner4};
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
     * 获取顶部图片高度后，设置滚动监听
     */
    private void initListeners() {

        ViewTreeObserver vto = mViewPager.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mViewPager.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                imageHeight = mViewPager.getHeight();

                mScrollView.setScrollViewListener(new GradationScrollView.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {
                        if (y <= 0) {   //设置标题的背景颜色
                            textView.setBackgroundColor(Color.argb((int) 0, 144,151,166));
                        } else if (y > 0 && y <= imageHeight) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                            float scale = (float) y / imageHeight;
                            float alpha = (255 * scale);
                            textView.setTextColor(Color.argb((int) alpha, 255,255,255));
                            textView.setBackgroundColor(Color.argb((int) alpha, 144,151,166));
                        } else {    //滑动到banner下面设置普通颜色
                            textView.setBackgroundColor(Color.argb((int) 255, 144,151,166));
                        }
                    }
                });
            }
        });
    }

}
