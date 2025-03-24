package com.qw.framework.app.dialog

import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.qw.framework.app.R
import com.qw.framework.ktx.inflate
import com.qw.framework.ui.BaseListFragment
import com.qw.recyclerview.core.BaseViewHolder

/**
 * author : qinwei@agora.io
 * date : 2025/3/24 14:29
 * description :
 */
class TestDialogFragment : BaseListFragment<MethodInfo>() {

    override fun initData() {
        modules.add(MethodInfo("AlertDialog") {
            AlertDialog.Builder(requireContext())
                .setTitle("Title")
                .setMessage("1234235325")
//                .setNeutralButton("Neutral") { dialog, which ->
//
//                }
                .setPositiveButton("Positive") { dialog, which ->

                }
                .setNegativeButton("Negative") { dialog, which ->

                }
                .show()
        })
        modules.add(MethodInfo("DialogFragment") {
            DialogFragment(R.layout.dialog_custom_center_layout).show(childFragmentManager, "")
        })

        modules.add(MethodInfo("CustomDialogFragment") {
            CustomDialogFragment().show(childFragmentManager, "")
        })

        modules.add(MethodInfo("BottomSheetDialog") {
            BottomSheetDialog(requireContext()).apply {
                this.setContentView(R.layout.dialog_custom_bottom_layout)
            }.show()
        })

        modules.add(MethodInfo("BottomSheetDialogFragment") {
            BottomSheetDialogFragment(R.layout.dialog_custom_bottom_layout).show(
                childFragmentManager,
                ""
            )
        })

        modules.add(MethodInfo("CustomBottomSheetDialogFragment") {
            CustomBottomSheetDialogFragment().show(childFragmentManager, "")
        })
        adapter.notifyDataSetChanged()
    }

    override fun onCreateBaseViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return object : BaseViewHolder(parent.inflate(android.R.layout.simple_list_item_1)) {
            init {
                itemView.setOnClickListener {
                    val item = model as MethodInfo
                    item.call.invoke(item)
                }
            }

            override fun initData(position: Int) {
                (itemView as TextView).text = modules[position].name
            }
        }
    }

}

data class MethodInfo(val name: String, val call: MethodInfo.() -> Unit)