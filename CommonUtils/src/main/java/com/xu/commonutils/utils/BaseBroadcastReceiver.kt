package com.coocent.volumebooster.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

/**
 * desc:
 **
 * user: xujj
 * time: 2022/9/26 14:39
 **/
class BaseBroadcastReceiver(context: Context?) : BroadcastReceiver() {

    interface OnReceiveCallback {
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

    fun register(callback: OnReceiveCallback?) {
        onReceiveCallback = callback
        mContext?.registerReceiver(this, intentFilter)
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
