package com.qw.framework.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.qw.framework.core.R
import java.util.ArrayList

/**
 * Created by qinwei on 2016/4/6 18:21
 * email:qinwei_it@163.com
 */
abstract class BaseViewPager2Activity<T> : BaseActivity() {
    protected lateinit var adapter: DataPageAdapter

    @JvmField
    protected val modules = ArrayList<T>()

    protected lateinit var mViewPager: ViewPager2
    override fun initView() {
        mViewPager = findViewById(R.id.mViewPager)
        mViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                this@BaseViewPager2Activity.onPageScrolled(
                    position,
                    positionOffset,
                    positionOffsetPixels
                )
            }

            override fun onPageSelected(position: Int) {
                this@BaseViewPager2Activity.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                this@BaseViewPager2Activity.onPageScrollStateChanged(state)
            }
        })
        initViewPager2(mViewPager)
        adapter = DataPageAdapter(this)
        setAdapter(adapter)
    }

    open fun initViewPager2(mViewPager: ViewPager2) {
        mViewPager.offscreenPageLimit = 3
    }

    fun setAdapter(adapter: FragmentStateAdapter?) {
        mViewPager.adapter = adapter
    }

    open fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
    open fun onPageScrollStateChanged(state: Int) {}
    open fun onPageSelected(position: Int) {}
    inner class DataPageAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun createFragment(position: Int): Fragment {
            return getFragmentAtPosition(position)
        }

        override fun getItemCount(): Int {
            return modules.size
        }
    }

    /**
     * 获取所有底部tab图片资源
     *
     * @param position
     * @return
     */
    abstract fun getFragmentAtPosition(position: Int): Fragment
}