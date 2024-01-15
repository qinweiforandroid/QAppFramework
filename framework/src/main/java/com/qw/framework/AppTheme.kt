package com.qw.framework

import android.app.Activity

object ThemeCompat {
    private var theme: ITheme? = null
    fun inject(theme: ITheme) {
        ThemeCompat.theme = theme
    }

    @JvmStatic
    fun setTheme(activity: Activity) {
        if (theme != null) {
            theme!!.setTheme(activity)
        }
    }
}

interface ITheme {
    fun setTheme(activity: Activity?)
}