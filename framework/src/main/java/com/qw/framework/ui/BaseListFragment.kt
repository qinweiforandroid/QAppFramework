package com.qw.framework.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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
import com.qw.recyclerview.layout.MyStaggeredGridLayoutManager
import com.qw.recyclerview.swiperefresh.template.SwipeListCompat

abstract class BaseListFragment<T> : BaseFragment(R.layout.base_list_layout),
    OnRefreshListener, OnLoadMoreListener {
    private lateinit var mListComponent: SwipeListCompat<T>
    val adapter: BaseListAdapter
        get() {
            return mListComponent.adapter
        }
    val modules: ArrayList<T>
        get() {
            return mListComponent.modules
        }
    val smart: ISmartRecyclerView
        get() = mListComponent.smart

    override fun initView(view: View) {
        val mRecyclerView = findViewById<RecyclerView>(R.id.mRecyclerView)
        val mSwipeRefresh = findViewById<SwipeRefreshLayout>(R.id.mSwipeRefreshLayout)
        mListComponent = object : SwipeListCompat<T>(mRecyclerView, mSwipeRefresh) {

            override fun getItemViewTypeByPosition(position: Int): Int {
                return this@BaseListFragment.getItemViewTypeByPosition(position)
            }


            override fun onCreateBaseViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
                return this@BaseListFragment.onCreateBaseViewHolder(
                    parent,
                    viewType
                )
            }
        }
        mListComponent.supportLoadMore(DefaultLoadMore(), this)
        smart.setLayoutManager(getLinearLayoutManager())
            .setRefreshEnable(false)
            .setLoadMoreEnable(false)
            .setOnRefreshListener(this)
    }

    override fun initData() {

    }

    override fun onRefresh() {}
    override fun onLoadMore() {}

    open fun getItemViewTypeByPosition(position: Int): Int {
        return 0
    }

    abstract fun onCreateBaseViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder

    fun getLinearLayoutManager(): MyLinearLayoutManager {
        return MyLinearLayoutManager(requireContext())
    }

    open fun getGridLayoutManager(spanCount: Int): MyGridLayoutManager {
        return mListComponent.getGridLayoutManager(spanCount)
    }

    fun getStaggeredGridLayoutManager(spanCount: Int): MyStaggeredGridLayoutManager {
        return MyStaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
    }
}