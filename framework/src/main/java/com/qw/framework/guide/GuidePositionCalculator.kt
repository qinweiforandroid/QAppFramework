package com.qw.framework.guide

import android.graphics.Point
import kotlin.math.max
import kotlin.math.min

object GuidePositionCalculator {

    /**
     * 根据 TargetView 的屏幕坐标和尺寸，以及 GuideContent 的测量尺寸，
     * 计算 GuideContent 的放置坐标。
     *
     * @param targetX TargetView 屏幕 x 坐标
     * @param targetY TargetView 屏幕 y 坐标
     * @param targetW TargetView 宽度
     * @param targetH TargetView 高度
     * @param contentW GuideContent 测量宽度
     * @param contentH GuideContent 测量高度
     * @param direction 引导内容相对于 TargetView 的显示方向
     * @return GuideContent 的放置坐标
     */
    fun calculatePosition(
        targetX: Int,
        targetY: Int,
        targetW: Int,
        targetH: Int,
        contentW: Int,
        contentH: Int,
        direction: Direction
    ): Point {
        val targetCenterX = targetX + targetW / 2
        val targetCenterY = targetY + targetH / 2

        val x: Int
        val y: Int

        when (direction) {
            Direction.TOP -> {
                x = targetCenterX - contentW / 2
                y = targetY - contentH
            }
            Direction.BOTTOM -> {
                x = targetCenterX - contentW / 2
                y = targetY + targetH
            }
            Direction.LEFT -> {
                x = targetX - contentW
                y = targetCenterY - contentH / 2
            }
            Direction.RIGHT -> {
                x = targetX + targetW
                y = targetCenterY - contentH / 2
            }
        }

        return Point(x, y)
    }

    /**
     * 将坐标钳制到屏幕范围内，确保 GuideContent 不超出屏幕边界。
     *
     * @param x GuideContent 的 x 坐标
     * @param y GuideContent 的 y 坐标
     * @param contentW GuideContent 宽度
     * @param contentH GuideContent 高度
     * @param screenW 屏幕宽度
     * @param screenH 屏幕高度
     * @return 钳制后的坐标
     */
    fun clampToBounds(
        x: Int,
        y: Int,
        contentW: Int,
        contentH: Int,
        screenW: Int,
        screenH: Int
    ): Point {
        val clampedX = max(0, min(x, screenW - contentW))
        val clampedY = max(0, min(y, screenH - contentH))
        return Point(clampedX, clampedY)
    }
}
