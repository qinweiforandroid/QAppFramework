package com.qw.framework.app.status

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.qw.framework.app.R
import com.qw.framework.page.IPageStatus

/**
 * create by qinwei at 2022/9/16 10:10
 */
class ErrorStatus(private val listener: OnRetryListener) : IPageStatus {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.status_empty_layout, container, true)
    }


    override fun onViewCreated(view: View) {
        view.findViewById<Button>(R.id.mRetryBtn).setOnClickListener {
            listener.onRetry()
        }
    }

    interface OnRetryListener {
        fun onRetry()
    }
}