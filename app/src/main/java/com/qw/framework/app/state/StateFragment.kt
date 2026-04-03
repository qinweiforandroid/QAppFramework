package com.qw.framework.app.state

import android.view.View
import android.widget.Button
import com.qw.framework.app.R
import com.qw.framework.page.PageStateLayout
import com.qw.framework.page.PageStateWrapper
import com.qw.framework.ui.BaseFragment

/**
 * author : qinwei@agora.io
 * date : 2026/4/3 15:30
 * description : PageState demo
 */
class StateFragment : BaseFragment(R.layout.fragment_state_test) {
    private lateinit var stateLayout: PageStateLayout

    override fun initView(view: View) {
        stateLayout = PageStateWrapper.wrap(view.findViewById(R.id.stateContent))

        view.findViewById<Button>(R.id.btnLoading).setOnClickListener {
            stateLayout.show(LoadingState())
        }
        view.findViewById<Button>(R.id.btnEmpty).setOnClickListener {
            stateLayout.show(EmptyState())
        }
        view.findViewById<Button>(R.id.btnError).setOnClickListener {
            stateLayout.show(ErrorState {
                stateLayout.showContent()
            })
        }
        view.findViewById<Button>(R.id.btnContent).setOnClickListener {
            stateLayout.showContent()
        }
    }

    override fun initData() {
    }
}
