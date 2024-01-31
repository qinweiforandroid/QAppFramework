package com.qw.framework.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.qw.framework.R;

import java.util.ArrayList;

/**
 * Created by qinwei on 2016/4/6 18:21
 * email:qinwei_it@163.com
 */
public abstract class BaseViewPagerActivity<T> extends BaseActivity implements ViewPager.OnPageChangeListener {
    protected DataPageAdapter adapter;
    protected ArrayList<T> modules = new ArrayList<>();
    protected ViewPager mViewPager;

    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        mViewPager.addOnPageChangeListener(this);
        initViewPager(mViewPager);
        adapter = new DataPageAdapter(getSupportFragmentManager());
        setAdapter(adapter);
    }

    private void initViewPager(ViewPager mViewPager) {
        mViewPager.setOffscreenPageLimit(3);
    }

    public void setAdapter(PagerAdapter adapter) {
        mViewPager.setAdapter(adapter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageSelected(int position) {
    }


    public class DataPageAdapter extends FragmentStatePagerAdapter {

        public DataPageAdapter(FragmentManager fm) {
            super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public Fragment getItem(int position) {
            return getFragmentAtPosition(position);
        }


        @Override
        public int getCount() {
            return modules.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getPageTitleAtPosition(position);
        }
    }

    protected abstract CharSequence getPageTitleAtPosition(int position);

    /**
     * 获取所有底部tab图片资源
     *
     * @param position
     * @return
     */
    public abstract Fragment getFragmentAtPosition(int position);
}
