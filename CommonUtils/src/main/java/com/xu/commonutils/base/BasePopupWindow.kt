package com.xu.commonutils.base

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.viewbinding.ViewBinding
import com.xu.commonutils.utils.BaseUtils

/**
 * desc:
 **
 * user: xujj
 * time: 2023/9/1 11:50
 **/
abstract class BasePopupWindow<T : ViewBinding>(context: Context) : PopupWindow(), OnViewClickListener {

    protected val binding: T

    init {
        binding = getViewBinding(LayoutInflater.from(context))
        contentView = binding.root
        isOutsideTouchable = true
        setBackgroundDrawable(ColorDrawable()) //Android5.0以下点击外部无法收起

        isFocusable = true
        if (isFullScreen()) {
            //全屏，不弹出导航栏
            contentView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }

    abstract fun getViewBinding(inflater: LayoutInflater): T

    protected open fun isFullScreen(): Boolean {
        return false
    }

    protected var onViewClickListener: OnViewClickListener? = null

    fun setOnViewClickListener(listener: OnViewClickListener?): BasePopupWindow<T> {
        onViewClickListener = listener
        return this
    }

    protected fun setClickViews(vararg views: View?) {
        for (view in views) {
            view?.setOnClickListener { v -> onViewClick(v, v.id) }
        }
    }

    override fun onViewClick(view: View, id: Int) {
        onViewClickListener?.onViewClick(view, id)
        dismiss()
    }

    fun show(anchor: View) {
        val anchorLoc = IntArray(2)
        anchor.getLocationInWindow(anchorLoc)
        //实际坐标中心点为触发view的中间，(anchorLoc[0],anchorLoc[1])为anchorView左上角
        val offsetX = if (BaseUtils.isRTL()) {
            anchorLoc[0]
        } else {
            anchorLoc[0] + anchor.width - width
        }
        val offsetY = anchorLoc[1] + anchor.height
        showAtLocation(anchor, Gravity.NO_GRAVITY, offsetX, offsetY)
    }
}