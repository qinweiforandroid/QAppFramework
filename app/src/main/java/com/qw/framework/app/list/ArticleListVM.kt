package com.qw.framework.app.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qw.framework.app.repository.SmartRefreshRepository
import com.qw.framework.app.repository.entities.ArticleBean
import com.qw.framework.ui.IPaging
import com.qw.recyclerview.page.DefaultPage
import com.qw.recyclerview.page.IPage
import kotlinx.coroutines.launch

/**
 * Created by qinwei on 2024/3/23 16:13
 * email: qinwei_it@163.com
 */
class ArticleListVM : ViewModel(), IPaging {

    private val page = DefaultPage(0)
    val articles = MutableLiveData<Result<ArrayList<ArticleBean>>>()

    override fun getPaging(): IPage {
        return page
    }

    override fun loadData() {
        viewModelScope.launch {
            try {
                val response = SmartRefreshRepository.loadArticles(page.getWillPage())
                if (response.errorCode == 0) {
                    val pageCount = response.data.pageCount ?: 0
                    articles.value = Result.success(response.data.datas!!.apply {
                        page.onPageChanged(page.getWillPage() >= pageCount)
                    })
                } else {
                    articles.value = Result.failure(RuntimeException(response.errorMsg))
                }
            } catch (e: Exception) {
                articles.value = Result.failure(e)
            }
        }
    }
}