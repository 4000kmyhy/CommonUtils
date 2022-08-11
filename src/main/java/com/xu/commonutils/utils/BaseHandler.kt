package com.xu.commonutils.utils

import android.os.Handler
import android.os.Looper
import android.os.Message
import java.lang.ref.WeakReference

/**
 * desc:
 **
 * user: xujj
 * time: 2022/8/11 9:59
 **/
abstract class BaseHandler(parent: Any?) : Handler(Looper.getMainLooper()) {

    private val weakReference: WeakReference<*>

    init {
        weakReference = WeakReference(parent)
    }

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        val parent = weakReference.get()
        doHandler(parent, msg)
    }

    protected abstract fun doHandler(parent: Any?, msg: Message?)
}