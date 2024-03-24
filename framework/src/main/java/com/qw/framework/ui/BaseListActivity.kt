package com.qw.framework.ui

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.qw.framework.R
import com.qw.recyclerview.core.BaseListAdapter
import com.qw.recyclerview.core.BaseViewHolder
import com.qw.recyclerview.core.ISmartRecyclerView
import com.qw.recyclerview.core.OnLoadMoreListener
import com.qw.recyclerview.core.OnRefreshListener
import com.qw.recyclerview.footer.DefaultLoadMore
import com.qw.recyclerview.layout.MyGridLayoutManager
import com.qw.recyclerview.layout.MyLinearLayoutManager
import com.qw.recyclerview.loadmore.AbsLoadMore
import com.qw.recyclerview.swiperefresh.SwipeRecyclerView
import com.qw.recyclerview.template.SmartListCompat

abstract class BaseListActivity<T> : BaseActivity(), OnRefreshListener, OnLoadMoreListener {
    private var mPageViewModel: IPaging? = null
    protected lateinit var mListComponent: SmartListCompat<T>
    val adapter: BaseListAdapter
        get() {
            return mListComponent.adapter
        }
    val modules: ArrayList<T>
        get() {
            return mListComponent.modules
        }
    lateinit var smart: ISmartRecyclerView
    override fun setContentView() {
        setContentView(R.layout.recyclerview_swiperefresh_layout, true)
    }

    override fun initView() {
        val mRecyclerView = findViewById<RecyclerView>(R.id.mRecyclerView)
        val mSwipeRefresh = findViewById<SwipeRefreshLayout>(R.id.mSwipeRefreshLayout)
        smart = SwipeRecyclerView(mRecyclerView, mSwipeRefresh)
        mListComponent = object : SmartListCompat<T>(smart) {

            override fun getItemViewTypeByPosition(position: Int): Int {
                return this@BaseListActivity.getItemViewTypeByPosition(position)
            }

            override fun onCreateBaseViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
                return this@BaseListActivity.onCreateBaseViewHolder(
                    parent,
                    viewType
                )
            }
        }
        mListComponent.setUpLoadMore(getLoadMore())
            .setOnLoadMoreListener(this)
            .setOnRefreshListener(this)
    }

    open fun getLoadMore(): AbsLoadMore {
        return DefaultLoadMore()
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    fun injectPaging(vm: IPaging) {
        this.mPageViewModel = vm
        mListComponent.setUpPage(vm.getPaging())
    }

    override fun onRefresh() {
        mPageViewModel?.onRefresh()
    }

    override fun onLoadMore() {
        mPageViewModel?.onLoadMore()
    }

    open fun getItemViewTypeByPosition(position: Int): Int {
        return 0
    }

    abstract fun onCreateBaseViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder

    open fun getGridLayoutManager(spanCount: Int): MyGridLayoutManager {
        return mListComponent.getGridLayoutManager(spanCount)
    }
}