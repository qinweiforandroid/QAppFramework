package com.qw.framework.guide

import android.app.Activity
import android.content.ContextWrapper
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout

class GuideOverlay private constructor(
    private val targetView: View,
    private val guideContent: View,
    private val direction: Direction,
    private val overlayBackgroundEnabled: Boolean,
    private val dismissOnTouchOutside: Boolean,
    private val backgroundColor: Int,
    private val onDismissListener: OnDismissListener?
) {
    private var overlayView: FrameLayout? = null
    private var visibilityListener: ViewTreeObserver.OnGlobalLayoutListener? = null

    fun show() {
        // 检查 Activity 是否有效
        val activity = getActivity(targetView) ?: return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed) return
        if (activity.isFinishing) return

        // 处理重复调用 show()：先 dismiss 再重新显示
        if (overlayView != null) {
            dismiss()
        }

        val decorView = activity.window.decorView as FrameLayout
        val context = targetView.context

        // 创建 overlayView
        val overlay = FrameLayout(context)
        overlay.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )

        // 若 overlayBackgroundEnabled 为 true，设置背景色并注册点击事件
        if (overlayBackgroundEnabled) {
            overlay.setBackgroundColor(backgroundColor)
            overlay.setOnClickListener { dismiss() }
        }

        // 若 dismissOnTouchOutside 为 true，注册点击事件
        if (dismissOnTouchOutside) {
            overlay.setOnClickListener { dismiss() }
        }

        this.overlayView = overlay

        // 定位并添加 GuideContent 的逻辑
        val positionAndAdd = Runnable {
            // 再次检查 Activity 有效性
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed) return@Runnable
            if (activity.isFinishing) return@Runnable

            // 获取 TargetView 和 DecorView 的屏幕坐标，计算相对偏移
            val targetLoc = IntArray(2)
            targetView.getLocationOnScreen(targetLoc)
            val decorLoc = IntArray(2)
            decorView.getLocationOnScreen(decorLoc)
            // 使用相对于 DecorView 的坐标
            val targetX = targetLoc[0] - decorLoc[0]
            val targetY = targetLoc[1] - decorLoc[1]
            val targetW = targetView.width
            val targetH = targetView.height

            // 如果 guideContent 已有 parent，先移除
            (guideContent.parent as? ViewGroup)?.removeView(guideContent)

            // 测量 GuideContent，使用 UNSPECIFIED 确保 wrap_content 的 view 能正确测量
            val screenW = decorView.width
            val screenH = decorView.height
            guideContent.measure(
                View.MeasureSpec.makeMeasureSpec(screenW, View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(screenH, View.MeasureSpec.AT_MOST)
            )
            var contentW = guideContent.measuredWidth
            var contentH = guideContent.measuredHeight

            // 如果测量结果为 0，使用 UNSPECIFIED 重新测量
            if (contentW == 0 || contentH == 0) {
                guideContent.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                )
                contentW = guideContent.measuredWidth
                contentH = guideContent.measuredHeight
            }

            // 计算位置
            val position = GuidePositionCalculator.calculatePosition(
                targetX, targetY, targetW, targetH, contentW, contentH, direction
            )
            val clamped = GuidePositionCalculator.clampToBounds(
                position.x, position.y, contentW, contentH, screenW, screenH
            )

            // 通过 LayoutParams 的 leftMargin/topMargin 定位
            val lp = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            lp.leftMargin = clamped.x
            lp.topMargin = clamped.y

            // 确保 guideContent 的点击不会冒泡到 overlayView
            guideContent.isClickable = true

            // 添加 GuideContent 到 overlayView
            overlay.addView(guideContent, lp)

            // 将 overlayView 添加到 DecorView
            decorView.addView(overlay)

            // 延迟启动可见性监听，确保布局完成后再开始检测
            overlay.post {
                startVisibilityMonitoring()
            }
        }

        // 等待 TargetView 布局完成
        if (targetView.width == 0 || targetView.height == 0) {
            targetView.post(positionAndAdd)
        } else {
            positionAndAdd.run()
        }
    }

    fun dismiss() {
        val overlay = overlayView ?: return

        // 停止可见性监听
        stopVisibilityMonitoring()

        // 从 DecorView 移除 overlayView
        (overlay.parent as? ViewGroup)?.removeView(overlay)

        // 回调 onDismissListener
        onDismissListener?.onDismiss()

        // 置空 overlayView
        overlayView = null
    }

    private fun startVisibilityMonitoring() {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            if (!isTargetVisible()) {
                dismiss()
            }
        }
        visibilityListener = listener
        targetView.viewTreeObserver.addOnGlobalLayoutListener(listener)
    }

    private fun stopVisibilityMonitoring() {
        val listener = visibilityListener ?: return
        val observer = targetView.viewTreeObserver
        if (observer.isAlive) {
            observer.removeOnGlobalLayoutListener(listener)
        }
        visibilityListener = null
    }

    private fun isTargetVisible(): Boolean {
        var view: View? = targetView
        while (view != null) {
            if (view.visibility != View.VISIBLE) return false
            view = view.parent as? View
        }
        return true
    }

    private fun getActivity(view: View): Activity? {
        var context = view.context
        while (context is ContextWrapper) {
            if (context is Activity) return context
            context = context.baseContext
        }
        return null
    }

    class Builder {
        private var targetView: View? = null
        private var guideContent: View? = null
        private var direction: Direction = Direction.BOTTOM
        private var overlayBackgroundEnabled: Boolean = true
        private var dismissOnTouchOutside: Boolean = false
        private var backgroundColor: Int = 0x80000000.toInt()
        private var onDismissListener: OnDismissListener? = null

        fun setTargetView(view: View): Builder {
            this.targetView = view
            return this
        }

        fun setGuideContent(view: View): Builder {
            this.guideContent = view
            return this
        }

        fun setDirection(direction: Direction): Builder {
            this.direction = direction
            return this
        }

        fun setOverlayBackgroundEnabled(enabled: Boolean): Builder {
            this.overlayBackgroundEnabled = enabled
            return this
        }

        fun setDismissOnTouchOutside(enabled: Boolean): Builder {
            this.dismissOnTouchOutside = enabled
            return this
        }

        fun setBackgroundColor(color: Int): Builder {
            this.backgroundColor = color
            return this
        }

        fun setOnDismissListener(listener: OnDismissListener): Builder {
            this.onDismissListener = listener
            return this
        }

        fun build(): GuideOverlay {
            val target = targetView
                ?: throw IllegalArgumentException("targetView must be set")
            val content = guideContent
                ?: throw IllegalArgumentException("guideContent must be set")

            return GuideOverlay(
                targetView = target,
                guideContent = content,
                direction = direction,
                overlayBackgroundEnabled = overlayBackgroundEnabled,
                dismissOnTouchOutside = dismissOnTouchOutside,
                backgroundColor = backgroundColor,
                onDismissListener = onDismissListener
            )
        }
    }
}
