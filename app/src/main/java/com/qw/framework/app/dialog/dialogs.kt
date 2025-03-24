package com.qw.framework.app.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.qw.framework.app.R

/**
 * author : qinwei@agora.io
 * date : 2025/3/24 15:01
 * description :
 */
class CustomDialogFragment : DialogFragment(R.layout.dialog_custom_center_layout) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_Dialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //root click dismiss
        view.setOnClickListener {
            if (isCancelable) {
                dismiss()
            }
        }
        //content setOnClick
    }
}

class CustomBottomSheetDialogFragment :
    BottomSheetDialogFragment(R.layout.dialog_custom_bottom_layout) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_BottomSheetDialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return (super.onCreateDialog(savedInstanceState) as BottomSheetDialog).apply {
            behavior.skipCollapsed = skipCollapsed()
            behavior.isDraggable = isDraggable()
            behavior.state = state()
        }
    }

    open fun state() = BottomSheetBehavior.STATE_EXPANDED
    open fun skipCollapsed() = false
    open fun isDraggable() = true
}