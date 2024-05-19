package com.qw.framework.app.sport

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.qw.utils.DensityUtil

/**
 * 1.绘制圆环
 * 2.绘制高亮圆环
 * Created by qinwei on 2024/4/4 13:21
 * email: qinwei_it@163.com
 */
class SportView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    val CYCLE_COLOR = Color.parseColor("#90A4AE")
    val HIGHLIGHT_COLOR = Color.parseColor("#FF4081")
    val RADIUS = DensityUtil.dip2px(100)
    val RING_WIDTH = DensityUtil.dip2px(20)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.textAlign = Paint.Align.CENTER
        this.textSize = DensityUtil.dip2px(50).toFloat()
    }
    private val fontMetrics: Paint.FontMetrics = Paint.FontMetrics()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //设置圆环样式
        paint.style = Paint.Style.STROKE
        paint.color = CYCLE_COLOR
        paint.strokeWidth = RING_WIDTH.toFloat()
        canvas.drawCircle(width / 2f, height / 2f, RADIUS.toFloat(), paint)
        //设置高亮圆环
        paint.strokeCap = Paint.Cap.ROUND
        paint.color = HIGHLIGHT_COLOR
        canvas.drawArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS, -90f, 225f, false, paint
        )
        paint.style = Paint.Style.FILL
        paint.getFontMetrics(fontMetrics)
        canvas.drawText(
            "abab",
            width / 2f,
            height / 2f - (fontMetrics.ascent + fontMetrics.descent) / 2f,
            paint
        )
    }
}