package com.qw.framework.app.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.qw.framework.app.R
import com.qw.framework.ui.BaseViewPager2Activity
import com.qw.framework.uitls.L
import com.qw.framework.widget.BaseTab
import com.qw.framework.widget.TabLayout
import com.qw.framework.widget.TabView

/**
 * Created by qinwei on 2024/1/17 21:55
 * email: qinwei_it@163.com
 */
class MainTabViewPagerActivity : BaseViewPager2Activity<BaseTab>(),
    TabLayout.OnTabClickListener {

    private lateinit var mTabLayout: TabLayout

    override fun setContentView() {
        setContentView(R.layout.activity_main_tab_with_viewpager2)
    }

    override fun initView() {
        super.initView()
        mViewPager.isUserInputEnabled = false
        mTabLayout = findViewById(R.id.mTabLayout)
    }

    override fun initData(savedInstanceState: Bundle?) {
        modules.add(
            TabView.Tab.Builder()
                .setImgResId(R.drawable.selector_tab_ui_btn)
                .setLabelResId(R.string.tab_home)
                .setLabelColorResId(R.color.selector_tab_label)
                .setClazz(TabPageFragment::class.java)
                .setArguments(Bundle().apply {
                    this.putString("title", getString(R.string.tab_home))
                })
                .builder()
        )
        modules.add(
            TabView.Tab.Builder()
                .setImgResId(R.drawable.selector_tab_mine_btn)
                .setLabelResId(R.string.tab_me)
                .setLabelColorResId(R.color.selector_tab_label)
                .setClazz(TabPageFragment::class.java)
                .setArguments(Bundle().apply {
                    this.putString("title", getString(R.string.tab_me))
                })
                .builder()
        )
        mTabLayout.initData(modules, this)
        adapter.notifyDataSetChanged()
    }

    override fun onPageSelected(position: Int) {
        mTabLayout.setCurrentTab(position)
    }

    override fun onTabItemClick(currentIndex: Int): Boolean {
        L.d("onTabItemClick:${currentIndex}")
        return true
    }

    override fun onTabSwitched(newIndex: Int, oldIndex: Int) {
        mViewPager.setCurrentItem(newIndex, false)
    }

    override fun getFragmentAtPosition(position: Int): Fragment {
        val item = modules[position]
        return item.clazz.getDeclaredConstructor().newInstance().apply {
            arguments = item.args
        }
    }
}