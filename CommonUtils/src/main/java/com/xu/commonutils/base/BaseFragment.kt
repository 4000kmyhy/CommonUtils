package com.xu.commonutils.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * desc:
 **
 * user: xujj
 * time: 2022/8/10 17:34
 **/
abstract class BaseFragment : Fragment(), OnViewClickListener {

    fun navigateTo(
        activity: FragmentActivity?,
        @IdRes containerViewId: Int,
        tag: String? = javaClass.simpleName
    ) {
        if (activity == null) return
        activity.supportFragmentManager.beginTransaction()
            .add(containerViewId, this, tag)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    fun navigateTo(
        fragment: Fragment?,
        @IdRes containerViewId: Int,
        tag: String? = javaClass.simpleName
    ) {
        if (fragment == null) return
        fragment.childFragmentManager.beginTransaction()
            .add(containerViewId, this, tag)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    fun replaceTo(
        activity: FragmentActivity?,
        @IdRes containerViewId: Int,
        tag: String? = javaClass.simpleName
    ) {
        if (activity == null) return
        activity.supportFragmentManager.beginTransaction()
            .replace(containerViewId, this, tag)
            .commitAllowingStateLoss()
    }

    fun replaceTo(
        fragment: Fragment?,
        @IdRes containerViewId: Int,
        tag: String? = javaClass.simpleName
    ) {
        if (fragment == null) return
        fragment.childFragmentManager.beginTransaction()
            .replace(containerViewId, this, tag)
            .commitAllowingStateLoss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun init(view: View)

    fun setOnViewClickListener(vararg views: View?) {
        for (view in views) {
            view?.setOnClickListener { v -> onViewClick(v, v.id) }
        }
    }

    override fun onViewClick(view: View, id: Int) {}
}