package com.xu.commonutils.utils

import android.content.Context

/**
 * desc:
 **
 * user: xujj
 * time: 2022/8/10 16:56
 **/
object PixelUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)  。
     */
    @JvmStatic
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 。
     */
    @JvmStatic
    fun px2dp(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px(像素)  。
     */
    @JvmStatic
    fun sp2px(context: Context, spValue: Float): Int {
        val scale = context.resources.displayMetrics.scaledDensity
        return (spValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp 。
     */
    @JvmStatic
    fun px2sp(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.scaledDensity
        return (pxValue / scale + 0.5f).toInt()
    }
}