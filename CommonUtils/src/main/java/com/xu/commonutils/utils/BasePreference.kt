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
    operator fun get(context: Context?, key: String?, defValue: Any?): Any? {
        return get(context, SHARE_DEF_NAME, key, defValue)
    }

    @JvmStatic
    operator fun get(context: Context?, name: String?, key: String?, defValue: Any?): Any? {
        if (context == null) return defValue
        val sp = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        if (defValue is String) {
            return sp.getString(key, defValue)
        } else if (defValue is Boolean) {
            return sp.getBoolean(key, defValue)
        } else if (defValue is Int) {
            return sp.getInt(key, defValue)
        } else if (defValue is Long) {
            return sp.getLong(key, defValue)
        } else if (defValue is Float) {
            return sp.getFloat(key, defValue)
        }
        return defValue
    }

    @JvmStatic
    operator fun set(context: Context?, key: String?, value: Any?) {
        set(context, SHARE_DEF_NAME, key, value)
    }

    @JvmStatic
    operator fun set(context: Context?, name: String?, key: String?, value: Any?) {
        if (context == null) return
        val sp = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        if (value is String) {
            sp.edit().putString(key, value).apply()
        } else if (value is Boolean) {
            sp.edit().putBoolean(key, value).apply()
        } else if (value is Int) {
            sp.edit().putInt(key, value).apply()
        } else if (value is Long) {
            sp.edit().putLong(key, value).apply()
        } else if (value is Float) {
            sp.edit().putFloat(key, value).apply()
        }
    }
}