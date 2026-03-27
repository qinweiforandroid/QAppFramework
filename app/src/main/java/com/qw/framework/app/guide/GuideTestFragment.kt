package com.qw.framework.app.guide

import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.qw.framework.app.R
import com.qw.framework.guide.Direction
import com.qw.framework.guide.GuideOverlay
import com.qw.framework.ui.BaseFragment

class GuideTestFragment : BaseFragment(R.layout.fragment_guide_test) {
    private lateinit var btnGuideTarget: Button

    override fun initView(view: View) {
        val btnGuideTop = view.findViewById<Button>(R.id.btnGuideTop)
        val btnGuideBottom = view.findViewById<Button>(R.id.btnGuideBottom)
        val btnGuideLeft = view.findViewById<Button>(R.id.btnGuideLeft)
        val btnGuideRight = view.findViewById<Button>(R.id.btnGuideRight)
        btnGuideTarget = view.findViewById(R.id.btnGuideTarget)

        btnGuideTop.setOnClickListener { showGuide(Direction.TOP) }
        btnGuideBottom.setOnClickListener { showGuide(Direction.BOTTOM) }
        btnGuideLeft.setOnClickListener { showGuide(Direction.LEFT) }
        btnGuideRight.setOnClickListener { showGuide(Direction.RIGHT) }
    }

    private fun showGuide(direction: Direction) {
        val guideContent = LayoutInflater.from(requireContext())
            .inflate(R.layout.view_guide_content, null)
        GuideOverlay.Builder()
            .setTargetView(btnGuideTarget)
            .setGuideContent(guideContent)
            .setDirection(direction)
            .setDismissOnTouchOutside(true)
            .setOverlayBackgroundEnabled(false)
            .build()
            .show()
    }

    override fun initData() {
    }
}
