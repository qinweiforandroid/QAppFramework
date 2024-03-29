package com.qw.framework.app.repository

import com.qw.framework.app.repository.entities.ArticleBean

/**
 * Created by qinwei on 2024/3/20 10:11
 * email: qinwei_it@163.com
 */
object SmartRefreshRepository {
    private val api = API.create<SmartRefreshAPIService>()

    suspend fun loadArticles(page: Int): WanAndroidResponse<WanAndroidList<ArticleBean>> {
        return api.loadArticles(page)
    }
}