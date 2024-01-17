package com.qw.framework.app

import androidx.appcompat.app.AppCompatActivity

/**
 * Created by qinwei on 2024/1/17 21:53
 * email: qinwei_it@163.com
 */
data class ActivityInfo(
    val clazz: Class<out AppCompatActivity>,
    val title: String,
    val cover: String
)