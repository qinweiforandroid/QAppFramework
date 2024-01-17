package com.qw.framework.app

import android.app.Application
import com.qw.framework.App

/**
 * Created by qinwei on 2024/1/16 21:17
 * email: qinwei_it@163.com
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        App.init(
            App.Builder(this)
                .setDebug(BuildConfig.DEBUG)
        )
    }
}