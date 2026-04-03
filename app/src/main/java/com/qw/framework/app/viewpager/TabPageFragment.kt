package com.qw.framework.app.viewpager

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.qw.framework.app.R
import com.qw.framework.app.state.LoadingState
import com.qw.framework.page.PageStateLayout
import com.qw.framework.page.PageStateWrapper
import com.qw.framework.ui.BaseFragment
import com.qw.framework.uitls.L

/**
 * Created by qinwei on 2024/1/17 22:15
 * email: qinwei_it@163.com
 */
class TabPageFragment : BaseFragment(R.layout.tab_page_fragment) {
    private lateinit var pageStatus: PageStateLayout
    private lateinit var mPageLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableLazyLoadData()
    }

    override fun initView(view: View) {
        mPageLabel = findViewById(R.id.mPageLabel)
        pageStatus = PageStateWrapper.wrap(mPageLabel)
        pageStatus.show(LoadingState())
    }

    override fun initData() {
        val title = arguments?.getString("title")
        L.d("initData $title")
        mPageLabel.text = title
        mPageLabel.postDelayed({
            pageStatus.showContent()
        }, 200)
    }
}