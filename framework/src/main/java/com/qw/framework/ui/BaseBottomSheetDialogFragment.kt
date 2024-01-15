package com.qw.framework.ui

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * fragment封装
 * Created by qinwei on 2017/4/16.
 */
abstract class BaseBottomSheetDialogFragment(@LayoutRes private val contentLayoutId: Int) :
    IFragment, BottomSheetDialogFragment() {

    private var isDataInitialed = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStyle()
    }

    open fun initStyle() {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(contentLayoutId, container, false)
    }

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

    open fun isDraggable() = true

    open fun peekHeight() = 0
    open fun maxHeight() = 0
    open fun skipCollapsed() = true

    open fun state() = BottomSheetBehavior.STATE_EXPANDED


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return (super.onCreateDialog(savedInstanceState) as BottomSheetDialog).apply {
            if (peekHeight() > 0) {
                behavior.peekHeight = peekHeight()
            }
            behavior.skipCollapsed = skipCollapsed()
            behavior.isDraggable = isDraggable()
            behavior.state = state()
            if (maxHeight() > 0) {
                window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, maxHeight())
                window!!.setGravity(Gravity.BOTTOM)
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        isDataInitialed = true
    }

    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }
}