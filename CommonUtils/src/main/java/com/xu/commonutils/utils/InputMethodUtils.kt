package com.xu.commonutils.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * desc:
 **
 * user: xujj
 * time: 2022/8/10 17:22
 **/
object InputMethodUtils {

    @JvmStatic
    fun showSoftKeyBoard(view: View?) {
        if (view == null || view.context == null) return
        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.requestFocus()
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    /**
     * 延迟显示软键盘，自动弹出时使用
     */
    @JvmStatic
    fun showSoftKeyBoardDelayed(view: View?) {
        if (view == null) return
        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.postDelayed(Runnable {
            if (view.context != null) {
                val imm =
                    view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }
        }, 300)
    }

    @JvmStatic
    fun hideSoftKeyBoard(view: View?): Boolean {
        if (view == null || view.context == null) return false
        view.clearFocus()
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    @JvmStatic
    fun hideSoftKeyBoard(activity: Activity?): Boolean {
        return hideSoftKeyBoard(activity?.currentFocus)
    }

    @Deprecated("")
    @JvmStatic
    fun toggleSoftKeyBoard(context: Context?) {
        if (context == null) return
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}