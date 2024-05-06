package com.xu.commonutils.utils

import android.app.ActivityManager
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import java.util.Locale

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

    fun getUid(context: Context): Int {
        try {
            val packageManager = context.packageManager
            val applicationInfo =
                packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            return applicationInfo.uid
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return -1
    }

    /**
     * 获取音频专辑图
     */
    @JvmStatic
    fun getAlbumArtUri(albumId: Long): Uri {
        return ContentUris.withAppendedId(
            Uri.parse("content://media/external/audio/albumart"),
            albumId
        )
    }

    @JvmStatic
    fun checkNull(o: Any?): Boolean {
        return o != null
    }

    @JvmStatic
    fun checkNull(vararg objects: Any?): Boolean {
        for (o in objects) {
            if (o == null) {
                return false
            }
        }
        return true
    }

    @JvmStatic
    fun startShake(view: View?) {
        view?.let {
            it.clearAnimation()
            val animation = TranslateAnimation(0f, 10f, 0f, 0f)
            animation.duration = 500
            animation.interpolator = CycleInterpolator(3f)
            it.startAnimation(animation)
        }
    }

    fun shareMusic(context: Context, id: Long) {
        try {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "audio/*"
            val uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(Intent.createChooser(intent, ""))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}