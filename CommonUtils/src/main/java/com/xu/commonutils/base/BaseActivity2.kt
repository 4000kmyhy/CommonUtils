package com.xu.commonutils.base

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding
import com.xu.commonutils.utils.StatusBarUtils

/**
 * desc:
 **
 * user: xujj
 * time: 2022/8/10 16:31
 **/
abstract class BaseActivity2<T : ViewBinding> : AppCompatActivity(), OnViewClickListener {

    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtils.init(this)
        binding = getViewBinding(layoutInflater)
        setContentView(binding.root)
        init()
    }

    protected abstract fun getViewBinding(inflater: LayoutInflater): T

    protected abstract fun init()

    fun setOnViewClickListener(vararg views: View?) {
        for (view in views) {
            view?.setOnClickListener { v -> onViewClick(v, v.id) }
        }
    }

    override fun onViewClick(view: View, id: Int) {}

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && isFullScreen()) {
            setFullScreen()
        }
    }

    protected open fun isFullScreen(): Boolean {
        return false
    }

    private fun setFullScreen() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        ViewCompat.getWindowInsetsController(window.decorView)
            ?.hide(WindowInsetsCompat.Type.navigationBars())
    }

    /**
     * 设置完全全屏
     */
    protected fun setCompleteFullScreen(b: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (b) {
                window.attributes = window.attributes.apply {
                    layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
                }
            } else {
                window.attributes = window.attributes.apply {
                    layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT
                }
            }
        }
    }
}