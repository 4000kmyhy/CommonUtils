package com.xu.commonutils.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.xu.commonutils.utils.FragmentUtils

/**
 * desc:
 **
 * user: xujj
 * time: 2022/8/10 17:34
 **/
abstract class BaseFragment2<T : ViewBinding> : Fragment(), OnViewClickListener {

    protected lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

    fun add(
        any: Any?,
        @IdRes containerViewId: Int,
        tag: String? = javaClass.simpleName,
        animationType: Int = FragmentUtils.ANIM_TYPE_NONE
    ) {
        FragmentUtils.addFragment(this, any, containerViewId, tag, animationType)
    }

    fun replace(
        any: Any?,
        @IdRes containerViewId: Int,
        tag: String? = javaClass.simpleName
    ) {
        FragmentUtils.replaceFragment(this, any, containerViewId, tag)
    }
}