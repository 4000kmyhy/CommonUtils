package com.xu.commonutils.utils

import android.graphics.LinearGradient
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape

/**
 * desc:
 **
 * user: xujj
 * time: 2023/3/16 11:11
 **/
class StrokeGradientDrawable(
    colors: IntArray,
    radius: Float,
    strokeWidth: Float,
    orientation: Int = TOP_BOTTOM
) : ShapeDrawable() {

    companion object {
        /** draw the gradient from the top to the bottom  */
        const val TOP_BOTTOM = 0

        /** draw the gradient from the top-right to the bottom-left  */
        const val TR_BL = 1

        /** draw the gradient from the right to the left  */
        const val RIGHT_LEFT = 2

        /** draw the gradient from the bottom-right to the top-left  */
        const val BR_TL = 3

        /** draw the gradient from the bottom to the top  */
        const val BOTTOM_TOP = 4

        /** draw the gradient from the bottom-left to the top-right  */
        const val BL_TR = 5

        /** draw the gradient from the left to the right  */
        const val LEFT_RIGHT = 6

        /** draw the gradient from the top-left to the bottom-right  */
        const val TL_BR = 7
    }

    init {
        // 外部矩形弧度
        val outerR = floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)
        // 内部矩形与外部矩形的距离
        val inset = RectF(strokeWidth, strokeWidth, strokeWidth, strokeWidth)
        // 内部矩形弧度
        val innerRadius = radius - strokeWidth
        val innerRadii = floatArrayOf(
            innerRadius,
            innerRadius,
            innerRadius,
            innerRadius,
            innerRadius,
            innerRadius,
            innerRadius,
            innerRadius
        )
        shape = RoundRectShape(outerR, inset, innerRadii)
        shaderFactory = object : ShaderFactory() {
            override fun resize(width: Int, height: Int): Shader {
                var x0 = 0f
                var y0 = 0f
                var x1 = 0f
                var y1 = 0f
                if (orientation == TOP_BOTTOM) {
                    x0 = 0f
                    y0 = 0f
                    x1 = 0f
                    y1 = height.toFloat()
                } else if (orientation == TR_BL) {
                    x0 = width.toFloat()
                    y0 = 0f
                    x1 = 0f
                    y1 = height.toFloat()
                } else if (orientation == RIGHT_LEFT) {
                    x0 = width.toFloat()
                    y0 = 0f
                    x1 = 0f
                    y1 = 0f
                } else if (orientation == BR_TL) {
                    x0 = width.toFloat()
                    y0 = height.toFloat()
                    x1 = 0f
                    y1 = 0f
                } else if (orientation == BOTTOM_TOP) {
                    x0 = 0f
                    y0 = height.toFloat()
                    x1 = 0f
                    y1 = 0f
                } else if (orientation == BL_TR) {
                    x0 = 0f
                    y0 = height.toFloat()
                    x1 = width.toFloat()
                    y1 = 0f
                } else if (orientation == LEFT_RIGHT) {
                    x0 = 0f
                    y0 = 0f
                    x1 = width.toFloat()
                    y1 = 0f
                } else if (orientation == TL_BR) {
                    x0 = 0f
                    y0 = 0f
                    x1 = width.toFloat()
                    y1 = height.toFloat()
                }
                return LinearGradient(
                    x0, y0, x1, y1,
                    colors, null,
                    Shader.TileMode.CLAMP
                )
            }
        }
    }
}