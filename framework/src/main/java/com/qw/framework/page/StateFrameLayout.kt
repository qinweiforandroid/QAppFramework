package com.qw.framework.page

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.forEach

/**
 * create by qinwei at 2022/9/9 17:59
 */
class StateFrameLayout(context: Context) : FrameLayout(context) {
    private var content: View? = null

    fun bind(content: View) {
        this.content = content
    }

    fun fill(content: View) {
        if (this.content != null) return
        content.layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        this.addView(content)
        bind(content)
    }

    fun show(status: IPageStatus) {
        removeAllStatusView()
        content?.visibility = View.INVISIBLE
        val view = status.onCreateView(LayoutInflater.from(context), this)
        status.onViewCreated(view)
    }

    private fun removeAllStatusView() {
        val cache = ArrayList<View>()
        forEach {
            if (content !== it) {
                cache.add(it)
            }
        }
        for (view in cache) {
            removeView(view)
        }
    }

    fun showTarget() {
        removeAllStatusView()
//        forEach {
//            if (content !== it) {
//                it.visibility = View.INVISIBLE
//            }
//        }
        content?.visibility = View.VISIBLE
    }
}