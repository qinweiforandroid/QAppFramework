package com.qw.framework.ui

import android.view.View
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

abstract class BaseListFragment<T> : BaseFragment(R.layout.recyclerview_swiperefresh_layout),
    OnRefreshListener, OnLoadMoreListener {
    protected lateinit var mListComponent: SmartListCompat<T>
    private var mPageViewModel: IPaging? = null
    val adapter: BaseListAdapter
        get() {
            return mListComponent.adapter
        }
    val modules: ArrayList<T>
        get() {
            return mListComponent.modules
        }
    lateinit var smart: ISmartRecyclerView

    override fun initView(view: View) {
        val mRecyclerView = findViewById<RecyclerView>(R.id.mRecyclerView)
        val mSwipeRefresh = findViewById<SwipeRefreshLayout>(R.id.mSwipeRefreshLayout)
        smart = SwipeRecyclerView(mRecyclerView, mSwipeRefresh)
        mListComponent = object : SmartListCompat<T>(smart) {

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
        mListComponent.setUpLayoutManager(getLinearLayoutManager())
            .setUpLoadMore(getLoadMore())
            .setOnLoadMoreListener(this)
            .setOnRefreshListener(this)
    }

    open fun getLoadMore(): AbsLoadMore {
        return DefaultLoadMore()
    }

    override fun initData() {

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

    fun getLinearLayoutManager(): MyLinearLayoutManager {
        return MyLinearLayoutManager(requireContext())
    }

    open fun getGridLayoutManager(spanCount: Int): MyGridLayoutManager {
        return mListComponent.getGridLayoutManager(spanCount)
    }
}