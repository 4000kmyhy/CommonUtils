package com.xu.commonutils.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.xu.commonutils.R
import com.xu.commonutils.utils.StatusBarUtils

/**
 * desc:
 **
 * user: xujj
 * time: 2022/8/11 10:26
 **/
abstract class BaseDialogFragment2<T : ViewBinding> : DialogFragment(), OnViewClickListener {

    protected lateinit var binding: T

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val window = dialog.window
            if (window != null) {
                if (isFullScreen()) {
//                    //状态栏、导航栏字体、图标颜色
//                    var visibility =
//                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    if (!isLight()) {
//                        //状态栏图标和文字颜色为暗色
//                        visibility = visibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//                        //导航栏图标和文字颜色为暗色
//                        visibility = visibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
//                    }
//                    window.decorView.systemUiVisibility = visibility
//
//                    //状态栏、导航栏背景透明
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        window.statusBarColor = Color.TRANSPARENT
//                        window.navigationBarColor = Color.TRANSPARENT
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { //系统栏设置不强调，否则不会完全透明
//                            window.isNavigationBarContrastEnforced = false
//                            window.isStatusBarContrastEnforced = false
//                        }
//                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) //设置透明状态栏
//                        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION) //设置透明导航栏
//                    }

                    StatusBarUtils.init(window, isLight())
                } else {
                    window.setWindowAnimations(R.style.dialogAnimation)
                    window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    window.setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    window.setGravity(Gravity.BOTTOM)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isFullScreen()) {
            setStyle(STYLE_NORMAL, R.style.Dialog_FullScreen_Theme)
        }
    }

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

    protected open fun isFullScreen(): Boolean {
        return false
    }

    protected open fun isLight(): Boolean {
        return true
    }
}