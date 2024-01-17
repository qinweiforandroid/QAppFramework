package com.qw.framework.app

import android.view.View
import android.widget.Toast
import com.qw.framework.ui.BaseFragment

/**
 * Created by qinwei on 2024/1/16 21:26
 * email: qinwei_it@163.com
 */
class MineFragment : BaseFragment(R.layout.tab_mine_fragment) {

    override fun initView(view: View) {

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!isHidden) {
            Toast.makeText(context, "MineFragment show", Toast.LENGTH_SHORT).show()
        }
    }

    override fun initData() {
    }
}