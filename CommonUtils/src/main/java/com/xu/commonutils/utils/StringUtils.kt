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
        return if (hh != 0L) {
            try {
                String.format("%02d:%02d:%02d", hh, mm, ss)
            } catch (e: OutOfMemoryError) {
                "00:00:00"
            }
        } else {
            try {
                String.format("%02d:%02d", mm, ss)
            } catch (e: OutOfMemoryError) {
                "00:00"
            }
        }
    }

    //String.format频繁调用会oom
    @JvmStatic
    fun stringForTime2(millisecond: Long): String {
        val second = millisecond / 1000
        val hh = second / 3600
        val mm = second % 3600 / 60
        val ss = second % 60
        return if (hh != 0L) {
            try {
                val hour = if (hh < 10) "0$hh" else "" + hh
                val min = if (mm < 10) "0$mm" else "" + mm
                val sec = if (ss < 10) "0$ss" else "" + ss
                "$hour:$min:$sec"
            } catch (e: OutOfMemoryError) {
                "00:00:00"
            }
        } else {
            try {
                val min = if (mm < 10) "0$mm" else "" + mm
                val sec = if (ss < 10) "0$ss" else "" + ss
                "$min:$sec"
            } catch (e: OutOfMemoryError) {
                "00:00"
            }
        }
    }

    /**
     * 获取00:00.0的时间
     */
    fun stringForMsTime(millisecond: Long): String {
        val second = millisecond / 1000.0
        val hh = (second / 3600).toInt()
        val mm = (second % 3600 / 60).toInt()
        val ss = (second % 60).toInt()
        val dec = (second % 60 * 10 % 10).toInt()
        return if (hh != 0) {
            try {
                String.format("%02d:%02d:%02d.%01d", hh, mm, ss, dec)
            } catch (e: OutOfMemoryError) {
                "00:00:00"
            }
        } else {
            try {
                String.format("%02d:%02d.%01d", mm, ss, dec)
            } catch (e: OutOfMemoryError) {
                "00:00"
            }
        }
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