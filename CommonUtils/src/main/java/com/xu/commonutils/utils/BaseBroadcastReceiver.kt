package com.xu.commonutils.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build

/**
 * desc:
 **
 * user: xujj
 * time: 2022/9/26 14:39
 **/
class BaseBroadcastReceiver(context: Context?) : BroadcastReceiver() {

    fun interface OnReceiveCallback {
        fun onReceive(context: Context, intent: Intent)
    }

    private val mContext: Context? = context
    private var onReceiveCallback: OnReceiveCallback? = null
    private val intentFilter = IntentFilter()

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null) {
            onReceiveCallback?.onReceive(context, intent)
        }
    }

    fun addAction(action: String): BaseBroadcastReceiver {
        intentFilter.addAction(action)
        return this
    }

    fun register(callback: OnReceiveCallback?): BaseBroadcastReceiver {
        onReceiveCallback = callback
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mContext?.registerReceiver(this, intentFilter, Context.RECEIVER_EXPORTED)
        } else {
            mContext?.registerReceiver(this, intentFilter)
        }
        return this
    }

//    fun register(vararg actions: String?) {
//        if (mContext == null || actions.isEmpty()) return
//        val filter = IntentFilter()
//        for (action in actions) {
//            filter.addAction(action)
//        }
//        mContext.registerReceiver(this, filter)
//    }

    fun unregister() {
        try {
            mContext?.unregisterReceiver(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
