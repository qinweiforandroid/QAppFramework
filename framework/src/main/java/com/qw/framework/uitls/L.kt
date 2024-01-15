package com.qw.framework.uitls

import android.util.Log
import com.qw.framework.App

object L {
    private var enable = true

    fun setEnable(enable: Boolean) {
        L.enable = enable
    }

    @JvmStatic
    fun d(msg: String) {
        if (App.isDebug() && enable) {
            Log.d("framework-core", msg)
        }
    }
}