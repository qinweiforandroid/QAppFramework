package com.qw.framework.page

import android.view.View
import android.view.ViewGroup

/**
 * create by qinwei at 2022/9/9 18:29
 */
class ViewTarget : ITarget {
    override fun wrapper(target: Any): StateFrameLayout {
        val panel = (target as View).parent as ViewGroup
        if (panel is StateFrameLayout) {
            //validate  重复添加
            return panel
        }
        val targetLayoutParams = target.layoutParams
        //拿到之前的位置
        val targetIndex = panel.indexOfChild(target)
        panel.removeView(target)
        val loadingView = StateFrameLayout(panel.context)
        loadingView.layoutParams = targetLayoutParams
        loadingView.fill(target)
        panel.addView(loadingView,targetIndex)
        return loadingView
    }
}