package com.qw.framework.ui.home

import android.content.Intent
import android.os.Bundle
import com.qw.framework.App
import com.qw.framework.R
import com.qw.framework.ui.BaseActivity
import com.qw.framework.uitls.L
import com.qw.widget.tab.BaseTab
import com.qw.widget.tab.TabLayout

/**
 * Created by qinwei on 2016/10/17 11:13
 * email:qinwei_it@163.com
 */
abstract class BaseHomeActivity : BaseActivity(), TabLayout.OnTabClickListener {

    open lateinit var mTabLayout: TabLayout
    private var tabs: ArrayList<BaseTab> = ArrayList()
    private var currentIndex = 0

    companion object {
        private const val KEY_CURRENT_INDEX = "key_current_index"
    }

    override fun initView() {
        mTabLayout = findViewById(R.id.mTabLayout)
    }

    override fun initData(savedInstanceState: Bundle?) {
        tabs.clear()
        initTabs(tabs)
        mTabLayout.initData(tabs, this)
        savedInstanceState?.let {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX)
            for (i in tabs.indices) {
                supportFragmentManager.findFragmentByTag("" + i)?.let {
                    supportFragmentManager.beginTransaction()
                        .hide(it)
                        .commitAllowingStateLoss()
                }
            }
        }
        mTabLayout.setCurrentTab(currentIndex)
    }

    abstract fun initTabs(tabs: ArrayList<BaseTab>)

    override fun onTabItemClick(index: Int, tab: BaseTab) {
        try {
            val fm = supportFragmentManager
            val ft = fm.beginTransaction()
            var to = fm.findFragmentByTag(index.toString())
            val from = fm.findFragmentByTag(currentIndex.toString())
            if (to == null) {
                to = tab.clazz.getDeclaredConstructor().newInstance()
                to.arguments = tab.args
                if (from == null) {
                    ft.add(R.id.mFragmentContainer, to, index.toString()).commitAllowingStateLoss()
                } else {
                    ft.hide(from)
                        .add(R.id.mFragmentContainer, to, index.toString())
                        .commitAllowingStateLoss()
                }
            } else {
                ft.hide(from!!).show(to).commitAllowingStateLoss()
            }
            setMenuId(tab.menuId)
            setTitle(tab.labelResId)
            currentIndex = index
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_CURRENT_INDEX, currentIndex)
        super.onSaveInstanceState(outState)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        //应用被强杀 app不会走onNewIntent方法
        val action = intent.getIntExtra(App.KEY_ACTION, App.ACTION_RESTART_APP)
        L.d("onNewIntent")
        when (action) {
            App.ACTION_RESTART_APP -> protectApp()
            App.ACTION_BACK_TO_HOME -> backToHome()
            App.ACTION_KICK_OUT -> kickOut()
            App.ACTION_LOGOUT -> logout()
            else -> {
                handlerAction(action, intent)
            }
        }
    }

    abstract fun handlerAction(action: Int, intent: Intent)

    open fun logout() {
        L.d("logout")
    }

    open fun kickOut() {
        L.d("kick out go to login")
    }

    open fun backToHome() {}

    open fun categoryHome() = false
    override fun onBackPressed() {
        if (categoryHome()) {
            //模拟点击home键
            val intent = Intent(Intent.ACTION_MAIN)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.addCategory(Intent.CATEGORY_HOME)
            startActivity(intent)
        } else {
            super.onBackPressed()
        }
    }

    override fun isDisplayHomeAsUpEnabled(): Boolean {
        return false
    }
}