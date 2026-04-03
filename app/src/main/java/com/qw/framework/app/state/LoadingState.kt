package com.qw.framework.app.state

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qw.framework.app.R
import com.qw.framework.page.PageState

class LoadingState : PageState {
    override fun createView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.state_loading_layout, container, true)
    }
}