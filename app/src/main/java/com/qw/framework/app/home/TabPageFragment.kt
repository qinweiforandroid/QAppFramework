package com.qw.framework.app.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.qw.framework.app.R
import com.qw.framework.app.status.LoadingStatus
import com.qw.framework.page.PageStatus
import com.qw.framework.page.StateFrameLayout
import com.qw.framework.ui.BaseFragment
import com.qw.framework.uitls.L

/**
 * Created by qinwei on 2024/1/17 22:15
 * email: qinwei_it@163.com
 */
class TabPageFragment : BaseFragment(R.layout.tab_page_fragment) {
    private lateinit var pageStatus: StateFrameLayout
    private lateinit var mPageLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableLazyLoadData()
    }

    override fun initView(view: View) {
        mPageLabel = findViewById(R.id.mPageLabel)
        pageStatus = PageStatus.wrap(mPageLabel)
        pageStatus.show(LoadingStatus())
    }

    override fun initData() {
        val title = arguments?.getString("title")
        L.d("initData $title")
        mPageLabel.text = title
        mPageLabel.postDelayed({
            pageStatus.showTarget()
        }, 200)
    }
}