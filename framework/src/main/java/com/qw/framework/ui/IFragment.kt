package com.qw.framework.ui

import android.os.Bundle
import android.view.View

/**
 * Created by qinwei on 2024/1/12 22:40
 * email: qinwei_it@163.com
 */
interface IFragment {
    /**
     * 初始化view
     *
     * @param view
     */
    fun initView(view: View)

    /**
     * 加载数据
     */
    fun initData()

    fun onRestoreInstanceState(savedInstanceState: Bundle)

    open fun onBackPressed() = false
}