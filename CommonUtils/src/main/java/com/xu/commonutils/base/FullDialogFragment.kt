package com.xu.commonutils.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.xu.commonutils.R
import com.xu.commonutils.utils.StatusBarUtils

/**
 * desc:
 **
 * user: xujj
 * time: 2022/8/11 10:26
 **/
abstract class FullDialogFragment<T : ViewBinding> : DialogFragment(), OnViewClickListener {

    protected lateinit var binding: T

    protected var isFullScreen = false
    protected var isCompleteFullScreen = false
    protected var fitsSystemWindows = false
    protected var isLight = true

    override fun onStart() {
        super.onStart()
        dialog?.window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setWindowAnimations(R.style.dialogAnimation)
            it.setGravity(Gravity.BOTTOM)
            if (isFullScreen) {//全屏
                it.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    if (isCompleteFullScreen) {//完全全屏，隐藏刘海屏
                        it.attributes = it.attributes.apply {
                            layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
                        }
                    } else {
                        it.attributes = it.attributes.apply {
                            layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT
                        }
                    }
                }
                it.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            } else {
                if (fitsSystemWindows) {//沉浸式状态栏、导航栏
                    StatusBarUtils.init(it, isLight)
                } else {
                    it.setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
            }
        }
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        if (isFullScreen()) {
//            setStyle(STYLE_NORMAL, R.style.Dialog_FullScreen_Theme)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return getViewBinding(inflater, container).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): T

    protected abstract fun init(view: View)

    fun setOnViewClickListener(vararg views: View?) {
        for (view in views) {
            view?.setOnClickListener { v -> onViewClick(v, v.id) }
        }
    }

    override fun onViewClick(view: View, id: Int) {}

    fun show(manager: FragmentManager) {
        show(manager, javaClass.simpleName)
    }
}