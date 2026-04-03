package com.qw.framework.page

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.forEach

/**
 * create by qinwei at 2022/9/9 17:59
 */
class PageStateLayout(context: Context) : FrameLayout(context) {
    private var contentView: View? = null

    fun setContentView(content: View) {
        if (this.contentView != null) return
        this.addView(content)
        this.contentView = content
    }

    fun show(state: PageState) {
        removeAllStateViews()
        contentView?.visibility = View.INVISIBLE
        state.createView(LayoutInflater.from(context), this)
    }

    fun showContent() {
        removeAllStateViews()
        contentView?.visibility = View.VISIBLE
    }

    private fun removeAllStateViews() {
        val cache = ArrayList<View>()
        forEach {
            if (contentView !== it) {
                cache.add(it)
            }
        }
        for (view in cache) {
            removeView(view)
        }
    }
}
