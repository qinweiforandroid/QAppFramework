package com.qw.framework.app.status

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qw.framework.app.R
import com.qw.framework.page.IPageStatus

/**
 * create by qinwei at 2022/9/15 18:26
 */
class LoadingStatus : IPageStatus {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.status_loading_layout, container, true)
    }

    override fun onViewCreated(view: View) {

    }
}