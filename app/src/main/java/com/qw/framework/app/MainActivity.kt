package com.qw.framework.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qw.framework.ui.home.BaseHomeActivity
import com.qw.widget.tab.BaseTab
import com.qw.widget.tab.TabView

class MainActivity : BaseHomeActivity() {
    override fun setContentView() {
        setContentView(R.layout.activity_main)
    }

    override fun initTabs(tabs: ArrayList<BaseTab>) {
        tabs.add(
            TabView.Tab.Builder()
                .setImgResId(R.drawable.selector_tab_ui_btn)
                .setLabelResId(R.string.tab_home)
                .setLabelColorResId(R.color.selector_tab_label)
                .setClazz(HomeFragment::class.java)
                .builder()
        )
//        tabs.add(EmptyTabView.Tab.newTab())
//        tabs.add(
//            TabView.Tab.Builder()
//                .setImgResId(R.drawable.selector_tab_frame_btn)
//                .setLabelResId(R.string.app_home_frame)
//                .setLabelColorResId(R.color.selector_tab_label)
//                .setArguments(Bundle().apply {
//                    putSerializable("cs", Clazz.genClazzList())
//                })
//                .setClazz(SupportListFragment::class.java)
//                .builder()
//        )

        tabs.add(
            TabView.Tab.Builder()
                .setImgResId(R.drawable.selector_tab_mine_btn)
                .setLabelResId(R.string.tab_me)
                .setLabelColorResId(R.color.selector_tab_label)
                .setClazz(MineFragment::class.java)
                .builder()
        )
    }

    override fun handlerAction(action: Int, intent: Intent) {

    }
}