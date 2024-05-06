package com.xu.commonutils.utils

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView

/**
 * desc:
 **
 * user: xujj
 * time: 2024/4/30 11:19
 **/
class LogManager {

    companion object {
        private var instance: LogManager? = null

        fun getInstance(): LogManager {
            if (instance == null) {
                synchronized(LogManager::class.java) {
                    instance = LogManager()
                }
            }
            return instance!!
        }
    }

    private var mWindowManager: WindowManager? = null
    private var mView: TextView? = null
    private val mLayoutParams = WindowManager.LayoutParams()

    private val mHandler = Handler(Looper.getMainLooper())

    init {
        mLayoutParams.type =
            WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL //TYPE_CHANGED  TYPE_APPLICATION_PANEL
        mLayoutParams.format = PixelFormat.RGBA_8888
        mLayoutParams.gravity = Gravity.BOTTOM
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        mLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
    }

    fun log(context: Context, msg: String) {
        if (mWindowManager == null) {
            mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        }
        if (mView == null) {
            mView = TextView(context)
            val padding = PixelUtils.dp2px(context, 10f)
            mView!!.setPadding(padding, padding, padding, padding)
            mView!!.setBackgroundColor(Color.parseColor("#F06060"))
            mView!!.setTextColor(Color.parseColor("#FFFFFF"))
            mView!!.textSize = 14f
            runOnUi {
                try {
                    mWindowManager!!.addView(mView, mLayoutParams)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        } else {
            runOnUi {
                try {
                    mWindowManager!!.updateViewLayout(mView, mLayoutParams)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
        runOnUi {
            mView!!.text = msg
        }

        mHandler.removeCallbacks(removeViewRunnable)
        mHandler.postDelayed(removeViewRunnable, 2000)
    }

    private val removeViewRunnable = Runnable {
        runOnUi {
            try {
                if (mView != null && mWindowManager != null) {
                    mWindowManager!!.removeViewImmediate(mView)
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }

        mView = null
        mWindowManager = null
    }

    private fun runOnUi(runnable: Runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run()
        } else {
            mHandler.post(runnable)
        }
    }
}