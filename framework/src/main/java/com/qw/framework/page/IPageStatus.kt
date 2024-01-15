package com.qw.framework.page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * create by qinwei at 2022/9/9 18:07
 */
interface IPageStatus {
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View
    fun onViewCreated(view: View)
}