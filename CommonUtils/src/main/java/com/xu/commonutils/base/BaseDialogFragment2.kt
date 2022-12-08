package com.xu.commonutils.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.xu.commonutils.R

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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        window.statusBarColor = Color.parseColor("#00000000")
                        window.navigationBarColor = Color.parseColor("#00000000")
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                    }
                    var visibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    if (!isLight()) {
                        //状态栏图标和文字颜色为暗色
                        visibility = visibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        //导航栏图标和文字颜色为暗色
                        visibility = visibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                    }
                    window.decorView.systemUiVisibility = visibility
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
        binding = getViewBinding(inflater, container)
        return binding.root
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