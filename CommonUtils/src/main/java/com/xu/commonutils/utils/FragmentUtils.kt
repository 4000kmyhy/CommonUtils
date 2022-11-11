package com.xu.commonutils.utils

import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.xu.commonutils.R

/**
 * desc:
 **
 * user: xujj
 * time: 2022/11/11 11:29
 **/
object FragmentUtils {

    const val ANIM_TYPE_NONE = 0
    const val ANIM_TYPE_TRANSLATE = 1
    const val ANIM_TYPE_SCALE = 2

    @JvmStatic
    fun addFragment(
        fragment: Fragment?,
        any: Any?,
        @IdRes containerViewId: Int,
        tag: String? = javaClass.simpleName,
        animationType: Int = ANIM_TYPE_NONE
    ) {
        var enter = 0
        var exit = 0
        var popEnter = 0
        var popExit = 0
        if (animationType == ANIM_TYPE_TRANSLATE) {
            enter = R.anim.fragment_slide_right_enter
            exit = R.anim.fragment_slide_left_exit
            popEnter = R.anim.fragment_slide_left_enter
            popExit = R.anim.fragment_slide_right_exit
        } else if (animationType == ANIM_TYPE_SCALE) {
            enter = R.anim.fragment_scale_enter
            exit = R.anim.fragment_scale_exit
            popEnter = R.anim.fragment_scale_enter
            popExit = R.anim.fragment_scale_exit
        }
        addFragment(
            fragment, any, containerViewId, tag,
            enter, exit, popEnter, popExit
        )
    }

    @JvmStatic
    fun addFragment(
        fragment: Fragment?,
        any: Any?,
        @IdRes containerViewId: Int,
        tag: String? = javaClass.simpleName,
        @AnimatorRes @AnimRes enter: Int = 0,
        @AnimatorRes @AnimRes exit: Int = 0,
        @AnimatorRes @AnimRes popEnter: Int = 0,
        @AnimatorRes @AnimRes popExit: Int = 0
    ) {
        if (fragment == null) return
        if (any is AppCompatActivity) {
            any.supportFragmentManager.beginTransaction()
                .setCustomAnimations(enter, exit, popEnter, popExit)
                .add(containerViewId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss()
        } else if (any is Fragment) {
            any.childFragmentManager.beginTransaction()
                .setCustomAnimations(enter, exit, popEnter, popExit)
                .add(containerViewId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }

    @JvmStatic
    fun replaceFragment(
        fragment: Fragment?,
        any: Any?,
        @IdRes containerViewId: Int,
        tag: String? = javaClass.simpleName
    ) {
        if (fragment == null) return
        if (any is AppCompatActivity) {
            any.supportFragmentManager.beginTransaction()
                .replace(containerViewId, fragment, tag)
                .commitAllowingStateLoss()
        } else if (any is Fragment) {
            any.childFragmentManager.beginTransaction()
                .replace(containerViewId, fragment, tag)
                .commitAllowingStateLoss()
        }
    }
}