package com.qw.framework.page

import android.app.Activity
import android.view.View


/**
 * create by qinwei at 2022/9/9 17:59
 */
object PageStatus {
    private fun wrapTarget(content: Any): StateFrameLayout {
        return TargetFactory.create(content).wrapper(content)
    }

    fun wrap(view: View): StateFrameLayout {
        return wrapTarget(view)
    }

    fun wrap(activity: Activity): StateFrameLayout {
        return wrapTarget(activity)
    }
}