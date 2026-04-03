package com.qw.framework.page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

interface PageState {
    fun createView(inflater: LayoutInflater, container: ViewGroup): View
}