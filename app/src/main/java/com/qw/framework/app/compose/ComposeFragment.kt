package com.qw.framework.app.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import com.qw.framework.ui.BaseFragment

/**
 * author : qinwei@agora.io
 * date : 2025/6/12 20:13
 * description :
 */
class ComposeFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnLifecycleDestroyed(this@ComposeFragment))
            setContent {
                HelloCompose()
            }
        }
    }

    @Composable
    private fun HelloCompose() {
        Text("Compose in Fragment")
    }

    @Composable
    @Preview(showBackground = true)
    private fun PreviewHelloCompose() {
        HelloCompose()
    }

    override fun initData() {

    }

    override fun initView(view: View) {
    }
}