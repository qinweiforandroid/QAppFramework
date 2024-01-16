package com.qw.framework.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qw.framework.ui.BaseFragment

/**
 * Created by qinwei on 2024/1/16 21:22
 * email: qinwei_it@163.com
 */
class HomeFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tab_home_fragment, container, false)
    }

    override fun initView(view: View) {

    }

    override fun initData() {

    }
}