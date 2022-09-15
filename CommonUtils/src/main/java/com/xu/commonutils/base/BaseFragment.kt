package com.xu.commonutils.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.xu.commonutils.R

/**
 * desc:
 **
 * user: xujj
 * time: 2022/8/10 17:34
 **/
abstract class BaseFragment : Fragment(), OnViewClickListener {

    var mEnterAnim = 0
    var mExitAnim = 0
    var mPopEnterAnim = 0
    var mPopExitAnim = 0

    fun withAnimations(
        @AnimatorRes @AnimRes enter: Int,
        @AnimatorRes @AnimRes exit: Int,
        @AnimatorRes @AnimRes popEnter: Int,
        @AnimatorRes @AnimRes popExit: Int
    ): BaseFragment {
        mEnterAnim = enter
        mExitAnim = exit
        mPopEnterAnim = popEnter
        mPopExitAnim = popExit
        return this
    }

    fun withTranslateAnimations(): BaseFragment {
        return withAnimations(
            R.anim.fragment_slide_right_enter, R.anim.fragment_slide_left_exit,
            R.anim.fragment_slide_left_enter, R.anim.fragment_slide_right_exit
        )
    }

    fun withScaleAnimations(): BaseFragment {
        return withAnimations(
            R.anim.fragment_scale_enter, R.anim.fragment_scale_exit,
            R.anim.fragment_scale_enter, R.anim.fragment_scale_exit
        )
    }

    fun navigateTo(
        activity: FragmentActivity?,
        @IdRes containerViewId: Int,
        tag: String? = javaClass.simpleName
    ) {
        if (activity == null) return
        activity.supportFragmentManager.beginTransaction()
            .setCustomAnimations(mEnterAnim, mExitAnim, mPopEnterAnim, mPopExitAnim)
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
            .setCustomAnimations(mEnterAnim, mExitAnim, mPopEnterAnim, mPopExitAnim)
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