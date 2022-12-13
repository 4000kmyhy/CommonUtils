package com.xu.commonutils.utils

import android.content.Context

/**
 * desc:
 **
 * user: xujj
 * time: 2022/8/11 10:01
 **/
object BasePreference {

    private const val SHARE_DEF_NAME = "CommonBase"

    @JvmStatic
    operator fun get(context: Context?, key: String, defValue: Any): Any {
        return get(context, SHARE_DEF_NAME, key, defValue)
    }

    @JvmStatic
    operator fun get(context: Context?, name: String, key: String, defValue: Any): Any {
        if (context == null) return defValue
        val sp = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        when (defValue) {
            is String -> {
                return sp.getString(key, defValue) ?: defValue
            }
            is Boolean -> {
                return sp.getBoolean(key, defValue)
            }
            is Int -> {
                return sp.getInt(key, defValue)
            }
            is Long -> {
                return sp.getLong(key, defValue)
            }
            is Float -> {
                return sp.getFloat(key, defValue)
            }
            else -> return defValue
        }
    }

    @JvmStatic
    operator fun set(context: Context?, key: String, value: Any) {
        set(context, SHARE_DEF_NAME, key, value)
    }

    @JvmStatic
    operator fun set(context: Context?, name: String, key: String, value: Any) {
        if (context == null) return
        val sp = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        when (value) {
            is String -> {
                sp.edit().putString(key, value).apply()
            }
            is Boolean -> {
                sp.edit().putBoolean(key, value).apply()
            }
            is Int -> {
                sp.edit().putInt(key, value).apply()
            }
            is Long -> {
                sp.edit().putLong(key, value).apply()
            }
            is Float -> {
                sp.edit().putFloat(key, value).apply()
            }
        }
    }
}