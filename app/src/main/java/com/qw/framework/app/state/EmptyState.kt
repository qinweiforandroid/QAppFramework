package com.qw.framework.app.state

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qw.framework.app.R
import com.qw.framework.page.PageState

class EmptyState : PageState {
    override fun createView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.state_empty_layout, container, true)
    }
}
