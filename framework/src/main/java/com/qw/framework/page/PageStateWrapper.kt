package com.qw.framework.page

import android.view.View
import android.view.ViewGroup

object PageStateWrapper {
    fun wrap(view: View): PageStateLayout {
        val panel = view.parent as ViewGroup
        if (panel is PageStateLayout) {
            return panel
        }
        val targetLayoutParams = view.layoutParams
        val targetIndex = panel.indexOfChild(view)
        panel.removeView(view)
        val stateLayout = PageStateLayout(panel.context)
        stateLayout.layoutParams = targetLayoutParams
        // view 进入 FrameLayout 容器后，原有的约束/线性等 LayoutParams 已无意义，
        // 改为 MATCH_PARENT 填满 stateLayout
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        stateLayout.setContentView(view)
        panel.addView(stateLayout, targetIndex)
        return stateLayout
    }
}
