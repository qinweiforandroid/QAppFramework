package com.qw.framework.app.state

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.qw.framework.app.R
import com.qw.framework.page.PageState

class ErrorState(private val listener: OnRetryListener) : PageState {
    override fun createView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.state_error_layout, container, true).also { view ->
            view.findViewById<Button>(R.id.mRetryBtn).setOnClickListener {
                listener.onRetry()
            }
        }
    }

    fun interface OnRetryListener {
        fun onRetry()
    }
}