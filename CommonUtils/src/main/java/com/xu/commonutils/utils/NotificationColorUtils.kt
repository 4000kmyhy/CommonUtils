package com.xu.commonutils.utils

import android.app.Notification
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import java.util.*

object NotificationColorUtils {

    private const val NOTIFICATION_TITLE = "notification_title"
    private const val INVALID_COLOR = -1 // 无效颜色
    private var notificationTitleColor = INVALID_COLOR // 获取到的颜色缓存

    @JvmStatic
    @ColorInt
    fun getNotificationTitleColor(
        context: Context,
        @LayoutRes layoutId: Int,
        @IdRes viewId: Int
    ): Int {
        var titleColor = getAndroidNotificationColor(context)
        if (titleColor == INVALID_COLOR) {
            titleColor = getDefNotificationColor(context, layoutId, viewId)
        }
        return titleColor
    }

    @JvmStatic
    fun getAndroidNotificationColor(context: Context): Int {
        try {
            if (notificationTitleColor == INVALID_COLOR) {
                notificationTitleColor = if (context is AppCompatActivity) {
                    getNotificationColorCompat(context)
                } else {
                    getNotificationColorInternal(context)
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return notificationTitleColor
    }

    private fun getNotificationColorInternal(context: Context): Int {
        val builder = Notification.Builder(context)
        builder.setContentTitle(NOTIFICATION_TITLE)
        val notification = builder.build()
        return try {
            val root = notification.contentView.apply(context, FrameLayout(context)) as ViewGroup
            val titleView = root.findViewById<View>(android.R.id.title) as TextView?
            if (null == titleView) {
                iteratorView(root, object : Filter {
                    override fun filter(view: View?) {
                        if (view is TextView) {
                            if (NOTIFICATION_TITLE == view.text.toString()) {
                                notificationTitleColor = view.currentTextColor
                            }
                        }
                    }
                })
                notificationTitleColor
            } else {
                titleView.currentTextColor
            }
        } catch (e: java.lang.Exception) {
            getNotificationColorCompat(context)
        }
    }

    private fun getNotificationColorCompat(context: Context): Int {
        try {
            val builder = Notification.Builder(context)
            val notification = builder.build()
            notification.contentView?.let {
                val layoutId = it.layoutId
                val root = LayoutInflater.from(context).inflate(layoutId, null) as ViewGroup
                val titleView = root.findViewById<View>(android.R.id.title) as TextView?
                return titleView?.currentTextColor ?: getTitleColorIteratorCompat(root)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return INVALID_COLOR
    }

    private fun iteratorView(view: View?, filter: Filter?) {
        if (view == null || filter == null) {
            return
        }
        filter.filter(view)
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child = view.getChildAt(i)
                iteratorView(child, filter)
            }
        }
    }

    private fun getTitleColorIteratorCompat(view: View?): Int {
        if (view == null) {
            return INVALID_COLOR
        }
        val textViews = getAllTextViews(view)
        val maxTextSizeIndex = findMaxTextSizeIndex(textViews)
        return if (maxTextSizeIndex != Int.MIN_VALUE) {
            textViews[maxTextSizeIndex].currentTextColor
        } else INVALID_COLOR
    }

    private fun findMaxTextSizeIndex(textViews: List<TextView>): Int {
        var max = Int.MIN_VALUE.toFloat()
        var maxIndex = Int.MIN_VALUE
        for ((index, textView) in textViews.withIndex()) {
            if (max < textView.textSize) {
                // 找到字号最大的字体，默认把它设置为主标题字号大小
                max = textView.textSize
                maxIndex = index
            }
        }
        return maxIndex
    }

    /**
     * 实现遍历View树中的TextView，返回包含TextView的集合。
     *
     * @param root 根节点
     * @return 包含TextView的集合
     */
    private fun getAllTextViews(root: View): List<TextView> {
        val textViews: MutableList<TextView> = ArrayList()
        iteratorView(root, object : Filter {
            override fun filter(view: View?) {
                if (view is TextView) {
                    textViews.add(view)
                }
            }
        })
        return textViews
    }

    private interface Filter {
        fun filter(view: View?)
    }

    @JvmStatic
    fun getDefNotificationColor(context: Context, layoutId: Int, textViewId: Int): Int {
        val viewGroup = LayoutInflater.from(context).inflate(layoutId, null, false) as ViewGroup
        return (viewGroup.findViewById<View>(android.R.id.title) as TextView?)?.currentTextColor
            ?: (viewGroup.findViewById<View>(textViewId) as TextView?)?.currentTextColor
            ?: findColor(viewGroup)
    }

    private fun findColor(viewGroupSource: ViewGroup): Int {
        var color = Color.TRANSPARENT
        val viewGroups = LinkedList<ViewGroup>()
        viewGroups.add(viewGroupSource)
        while (viewGroups.size > 0) {
            val viewGroup1 = viewGroups.first
            for (i in 0 until viewGroup1.childCount) {
                if (viewGroup1.getChildAt(i) is ViewGroup) {
                    viewGroups.add(viewGroup1.getChildAt(i) as ViewGroup)
                } else if (viewGroup1.getChildAt(i) is TextView) {
                    if ((viewGroup1.getChildAt(i) as TextView).currentTextColor != -1) {
                        color = (viewGroup1.getChildAt(i) as TextView).currentTextColor
                    }
                }
            }
            viewGroups.remove(viewGroup1)
        }
        return color
    }
}