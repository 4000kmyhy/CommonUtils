package com.xu.commonutils.adapter

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

/**
 * desc:
 **
 * user: xujj
 * time: 2022/8/16 14:37
 **/
open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun <T : View?> getView(@IdRes viewId: Int): T {
        val view = itemView.findViewById<View>(viewId)
        return view as T
    }
}