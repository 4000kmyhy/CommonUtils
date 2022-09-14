package com.xu.commonutils.utils

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import java.text.DecimalFormat
import java.util.*

/**
 * desc:
 **
 * user: xujj
 * time: 2022/8/10 17:04
 **/
object StringUtils {

    @JvmStatic
    fun stringForTime(millisecond: Long): String {
        val second = millisecond / 1000
        val hh = second / 3600
        val mm = second % 3600 / 60
        val ss = second % 60
        var str = "00:00"
        str = if (hh != 0L) {
            String.format("%02d:%02d:%02d", hh, mm, ss)
        } else {
            String.format("%02d:%02d", mm, ss)
        }
        return str
    }

    @JvmStatic
    fun stringForHour(millisecond: Long): String {
        val second = millisecond / 1000
        val hh = second / 3600
        val mm = second % 3600 / 60
        val ss = second % 60
        return String.format("%02d:%02d:%02d", hh, mm, ss)
    }

    @JvmStatic
    fun stringForSize(size: Long): String {
        val fileOrFilesSize = size * 1.0 / (1024 * 1024)
        return String.format("%.1fMB", fileOrFilesSize)
    }

    @JvmStatic
    fun stringForSpeed(speed: Double): String {
        val showFloatFormat = DecimalFormat("0.0")
        return if (speed >= 1024.0 * 1024.0) {
            showFloatFormat.format(speed / (1024.0 * 1024.0)) + " MB/s"
        } else if (speed >= 1024.0) {
            showFloatFormat.format(speed / 1024.0) + " KB/s"
        } else {
            showFloatFormat.format(speed) + " B/s"
        }
    }

    @JvmStatic
    fun stringForMemory(memory: Long): String {
        val showFloatFormat = DecimalFormat("0.0")
        return if (memory >= 1024.0 * 1024.0 * 1024.0) {
            showFloatFormat.format(memory / (1024.0 * 1024.0 * 1024.0)) + " GB"
        } else if (memory >= 1024.0 * 1024.0) {
            showFloatFormat.format(memory / (1024.0 * 1024.0)) + " MB"
        } else if (memory >= 1024.0) {
            showFloatFormat.format(memory / 1024.0) + " KB"
        } else {
            showFloatFormat.format(memory) + " B"
        }
    }

    /**
     * 获取高亮文本
     */
    @JvmStatic
    fun getSpanText(text: String?, searchText: String?, color: Int): SpannableStringBuilder {
        val builder = SpannableStringBuilder(text)
        if (!TextUtils.isEmpty(text) && !TextUtils.isEmpty(searchText)) {
            val index = text!!.lowercase(Locale.getDefault())
                .indexOf(searchText!!.lowercase(Locale.getDefault()))
            if (index > -1) {
                try {
                    builder.setSpan(
                        ForegroundColorSpan(color),
                        index, index + searchText.length,
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                    )
                } catch (e: Exception) {
                    e.printStackTrace() //越界
                }
            }
        }
        return builder
    }

    /**
     * 过滤特殊字符
     */
    @JvmStatic
    fun patternStr(title: String): Boolean {
        val length = title.length
        for (i in 0 until length) {
            val c = title[i]
            if (c == '.' || c == ',' || c == '/' || c == '\\' || c == '@' || c == '#' || c == '*' || c == '&' || c.toInt() == 12290 || c == ':') {
                return false
            }
        }
        return true
    }
}