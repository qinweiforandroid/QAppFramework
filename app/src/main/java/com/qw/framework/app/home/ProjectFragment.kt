package com.qw.framework.app.home

import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import com.qw.framework.app.compose.ComposeFragment
import com.qw.framework.app.dialog.TestDialogFragment
import com.qw.framework.app.pick.PickPhotoFragment
import com.qw.framework.ui.BaseListFragment
import com.qw.framework.ui.tools.ContainerClazz
import com.qw.framework.ui.tools.ContainerFragmentActivity
import com.qw.recyclerview.core.BaseViewHolder
import com.qw.recyclerview.footer.DefaultLoadMore
import com.qw.recyclerview.loadmore.AbsLoadMore

/**
 * Created by qinwei on 2024/1/16 21:22
 * email: qinwei_it@163.com
 */
class ProjectFragment : BaseListFragment<ContainerClazz>() {

    override fun initView(view: View) {
        super.initView(view)
        smart.setLayoutManager(getGridLayoutManager(2))
        modules.add(ContainerClazz("PhotoPick", PickPhotoFragment::class.java))
        modules.add(ContainerClazz("Compose", ComposeFragment::class.java))
        modules.add(ContainerClazz("Dialog", TestDialogFragment::class.java))
        adapter.notifyDataSetChanged()
    }

    override fun initData() {

    }

    override fun getLoadMore(): AbsLoadMore {
        return DefaultLoadMore()
    }

    override fun onCreateBaseViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return Holder(layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false))
    }

    inner class Holder(itemView: View) : BaseViewHolder(itemView), OnClickListener {
        private lateinit var item: ContainerClazz
        private val mHomeItemTitleLabel: TextView = itemView as TextView

        init {
            itemView.setOnClickListener(this)
        }

        override fun initData(position: Int) {
            item = model as ContainerClazz
            mHomeItemTitleLabel.text = item.title
        }

        override fun onClick(v: View) {
            startActivity(ContainerFragmentActivity.getIntent(requireContext(), item))
        }
    }
}

