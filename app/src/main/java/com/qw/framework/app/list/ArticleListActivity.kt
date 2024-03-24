package com.qw.framework.app.list

import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.qw.framework.app.R
import com.qw.framework.app.repository.entities.ArticleBean
import com.qw.framework.ktx.inflate
import com.qw.framework.ui.BaseListActivity
import com.qw.recyclerview.core.BaseViewHolder

/**
 * Created by qinwei on 2024/3/23 16:09
 * email: qinwei_it@163.com
 */
class ArticleListActivity : BaseListActivity<ArticleBean>() {
    private lateinit var mVM: ArticleListVM
    override fun setContentView() {
        setContentView(R.layout.activity_article_list, true)
    }

    override fun initData(savedInstanceState: Bundle?) {
        setTitle(intent.getStringExtra("title"))
        mListComponent.setLoadMoreEnable(true).setRefreshEnable(true)
        mVM = ViewModelProvider(this)[ArticleListVM::class.java]
        injectPaging(mVM)
        mVM.articles.observe(this) {
            val data = it.getOrNull()
            if (data == null) {
                mListComponent.notifyError()
            } else {
                mListComponent.notifyDataChanged(data)
            }
        }
        mListComponent.autoRefresh()
    }

    override fun onCreateBaseViewHolder(parent: ViewGroup, viewType: Int) = object : BaseViewHolder(
        parent.inflate(R.layout.activity_article_item)
    ) {
        val mTitleLabel = itemView.findViewById<TextView>(R.id.mItemTitleLabel)
        val mItemAuthorLabel = itemView.findViewById<TextView>(R.id.mItemAuthorLabel)
        override fun initData(position: Int) {
            val item = model as ArticleBean
            mTitleLabel.text = item.title
            if (item.author.isNullOrBlank()) {
                mItemAuthorLabel.text = "分享人:${item.shareUser}"
            } else {
                mItemAuthorLabel.text = "作者:${item.author}"
            }
            mItemAuthorLabel.append("\t时间:${item.niceDate}")
        }
    }
}