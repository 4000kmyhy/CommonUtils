package com.xu.commonutils.utils

import android.app.ActivityManager
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import java.util.*

/**
 * desc:
 **
 * user: xujj
 * time: 2022/8/10 17:02
 **/
object BaseUtils {

    /**
     * 服务是否在运行
     */
    @JvmStatic
    fun isServiceRunning(context: Context?, serviceName: String?): Boolean {
        if (context == null) return false
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (TextUtils.equals(context.packageName, service.service.packageName) &&
                TextUtils.equals(serviceName, service.service.className)
            ) {
                return true
            }
        }
        return false
    }

    /**
     * 是否为阿拉伯布局
     */
    @JvmStatic
    fun isRTL(): Boolean {
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_RTL
    }

    /**
     * 判断颜色是否为浅色
     */
    @JvmStatic
    fun isColorLight(@ColorInt color: Int): Boolean {
        val light = ColorUtils.calculateLuminance(color)
        return light >= 0.5
    }

    /**
     * 颜色加透明度
     */
    @JvmStatic
    fun getAlphaColor(color: Int, alpha: Float): Int {
        val a = Math.min(255, Math.max(0, (alpha * 255).toInt())) shl 24
        val rgb = 0x00ffffff and color
        return a + rgb
    }

    /**
     * 获取版本名
     */
    @JvmStatic
    fun getVersionName(context: Context): String? {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            return packageInfo.versionName
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "1.0.0"
    }

    /**
     * 获取音频专辑图
     */
    @JvmStatic
    fun getAlbumArtUri(albumId: Long): Uri? {
        return ContentUris.withAppendedId(
            Uri.parse("content://media/external/audio/albumart"),
            albumId
        )
    }
}