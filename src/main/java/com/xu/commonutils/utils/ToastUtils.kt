package com.xu.commonutils.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * desc:
 **
 * user: xujj
 * time: 2022/8/10 17:16
 **/
object ToastUtils {

    @JvmStatic
    fun showToast(context: Context?, text: CharSequence?) {
        runOnUi {
            if (context != null) {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
            }
        }
    }

    @JvmStatic
    fun showToast(context: Context?, @StringRes resId: Int) {
        runOnUi {
            if (context != null) {
                Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
            }
        }
    }

    @JvmStatic
    fun showLongToast(context: Context?, text: CharSequence?) {
        runOnUi {
            if (context != null) {
                Toast.makeText(context, text, Toast.LENGTH_LONG).show()
            }
        }
    }

    @JvmStatic
    fun showLongToast(context: Context?, @StringRes resId: Int) {
        runOnUi {
            if (context != null) {
                Toast.makeText(context, resId, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun runOnUi(runnable: Runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run()
        } else {
            Handler(Looper.getMainLooper()).post(runnable)
        }
    }
}