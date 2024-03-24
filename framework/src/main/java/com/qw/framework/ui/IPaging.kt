package com.qw.framework.ui

import com.qw.recyclerview.page.IPage

/**
 * Created by qinwei on 2024/3/24 13:47
 * email: qinwei_it@163.com
 */
interface IPaging {
    fun getPaging(): IPage
    fun onRefresh() {
        getPaging().pullToDown()
        loadData()
    }

    fun onLoadMore() {
        getPaging().pullToUp()
        loadData()
    }

    fun loadData()
}