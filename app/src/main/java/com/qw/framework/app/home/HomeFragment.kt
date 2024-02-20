package com.qw.framework.app.home

import android.content.Intent
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.qw.framework.app.R
import com.qw.framework.app.viewpager.MainTabViewPagerActivity
import com.qw.framework.ui.BaseListFragment
import com.qw.recyclerview.core.BaseViewHolder
import com.qw.recyclerview.footer.DefaultLoadMore
import com.qw.recyclerview.loadmore.AbsLoadMore

/**
 * Created by qinwei on 2024/1/16 21:22
 * email: qinwei_it@163.com
 */
class HomeFragment : BaseListFragment<ActivityInfo>() {

    override fun initView(view: View) {
        super.initView(view)
        smart.setLayoutManager(getGridLayoutManager(2))
        modules.add(
            ActivityInfo(
                MainTabViewPagerActivity::class.java,
                "MainTabViewPager",
                "https://img2.baidu.com/it/u=762599636,1175376405&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=680"
            )
        )
        adapter.notifyDataSetChanged()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!isHidden) {
            Toast.makeText(context, "HomeFragment show", Toast.LENGTH_SHORT).show()
        }
    }

    override fun initData() {

    }

    override fun getLoadMore(): AbsLoadMore {
        return DefaultLoadMore()
    }

    override fun onCreateBaseViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return Holder(layoutInflater.inflate(R.layout.module_list_item_layout, parent, false))
    }

    inner class Holder(itemView: View) : BaseViewHolder(itemView), OnClickListener {
        private lateinit var item: ActivityInfo
        private val mHomeItemPictureImg: ImageView
        private val mHomeItemTitleLabel: TextView

        init {
            mHomeItemPictureImg = itemView.findViewById<View>(R.id.mHomeItemPictureImg) as ImageView
            mHomeItemTitleLabel = itemView.findViewById<View>(R.id.mHomeItemTitleLabel) as TextView
            itemView.setOnClickListener(this)
        }

        override fun initData(position: Int) {
            item = model as ActivityInfo
            mHomeItemTitleLabel.text = item.title
            Glide.with(this@HomeFragment).load(item.cover).into(mHomeItemPictureImg)
        }

        override fun onClick(v: View) {
            val intent = Intent(context, item.clazz)
            startActivity(intent)
        }
    }
}
data class ActivityInfo(
    val clazz: Class<out AppCompatActivity>,
    val title: String,
    val cover: String
)
