package com.qw.framework.page

import android.app.Activity
import android.view.View

/**
 * create by qinwei at 2022/9/9 18:30
 */
object TargetFactory {
    fun create(content: Any): ITarget {
        return when (content) {
            is Activity -> {
                ActivityTarget()
            }
            is View -> {
                ViewTarget()
            }
            else -> {
                throw IllegalArgumentException("not support type:${content.javaClass}")
            }
        }
    }
}