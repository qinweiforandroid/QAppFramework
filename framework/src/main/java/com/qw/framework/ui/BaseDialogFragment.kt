package com.qw.framework.ui

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialogFragment

/**
 * Created by qinwei on 2024/1/12 23:01
 * email: qinwei_it@163.com
 */
abstract class BaseDialogFragment(@LayoutRes contentLayoutId: Int) :
    AppCompatDialogFragment(contentLayoutId), IFragment {
    private var isDataInitialed = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStyle()
    }

    open fun initStyle() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState)
        }
        if (!isDataInitialed) {
            initData()
        }
        isDataInitialed = true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            initDialog(this)
        }
    }

    open fun initDialog(dialog: Dialog) {}

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        isDataInitialed = true
    }
}