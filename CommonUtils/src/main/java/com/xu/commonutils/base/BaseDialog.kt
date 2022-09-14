package com.xu.commonutils.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import com.xu.commonutils.utils.InputMethodUtils

/**
 * desc:
 **
 * user: xujj
 * time: 2022/8/10 17:39
 **/
abstract class BaseDialog @JvmOverloads constructor(context: Context, themeResId: Int = 0) :
    Dialog(context, themeResId) {

    private var focusEditText: EditText? = null

    override fun onStart() {
        super.onStart()
        val window = window
        if (window != null) {
            window.setGravity(Gravity.CENTER)
            window.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            if (isTransBg()) {
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view = LayoutInflater.from(context).inflate(getLayoutId(), null)
        setContentView(view)
        init(view)
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun init(view: View)

    protected fun setFocusEditText(editText: EditText?) {
        focusEditText = editText
    }

    override fun show() {
        super.show()
        InputMethodUtils.showSoftKeyBoardDelayed(focusEditText)
    }

    override fun dismiss() {
        InputMethodUtils.hideSoftKeyBoard(focusEditText)
        super.dismiss()
    }

    protected open fun isTransBg(): Boolean {
        return false
    }

    protected fun setButtonEnable(textView: TextView, enable: Boolean) {
        textView.alpha = if (enable) 1f else 0.5f
        textView.isEnabled = enable
    }

    protected fun setButtonEnable(textView: TextView, enable: Boolean, @ColorInt textColor: Int) {
        textView.setTextColor(textColor)
        textView.isEnabled = enable
    }
}
