package com.qw.framework.app.home

import android.os.Build
import android.view.View
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import android.webkit.WebView
import android.widget.Toast
import com.qw.framework.app.R
import com.qw.framework.ui.BaseFragment


/**
 * Created by qinwei on 2024/1/16 21:26
 * email: qinwei_it@163.com
 */
class MineFragment : BaseFragment(R.layout.tab_mine_fragment) {

    override fun initView(view: View) {
        val mWebView = view.findViewById<WebView>(R.id.mWebView)
        val webSettings = mWebView.settings
        // 设置与Js交互的权限
        webSettings.javaScriptEnabled = true
        val url = "https://www.baidu.com"
        setCookie(url)
        mWebView.loadUrl(url)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!isHidden) {
            Toast.makeText(context, "MineFragment show", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setCookie(url: String) {
        CookieSyncManager.createInstance(requireContext())
        val cookieManager: CookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        //value的格式为   key=value  key：元数据标识，value元数据的值
        cookieManager.setCookie(url, "platform=android")
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.getInstance().sync()
        } else {
            CookieManager.getInstance().flush()
        }
    }

    override fun initData() {
    }
}