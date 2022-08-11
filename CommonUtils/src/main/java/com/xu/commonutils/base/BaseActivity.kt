package com.xu.commonutils.base

import android.os.Bundle
import android.os.Message
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.xu.commonutils.utils.BaseHandler
import com.xu.commonutils.utils.StatusBarUtils

/**
 * desc:
 **
 * user: xujj
 * time: 2022/8/10 16:31
 **/
abstract class BaseActivity : AppCompatActivity(), OnViewClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtils.init(this)
        setContentView(getLayoutId())
        init()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun init()

    fun setOnViewClickListener(vararg views: View?) {
        for (view in views) {
            view?.setOnClickListener { v -> onViewClick(v, v.id) }
        }
    }

    override fun onViewClick(view: View?, id: Int) {}
}