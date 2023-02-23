package com.xu.commonutils.utils

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources.NotFoundException
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat

/**
 * desc:
 **
 * user: xujj
 * time: 2022/8/11 10:39
 **/
object ImageUtils {

    @JvmStatic
    fun getDrawable(context: Context, @DrawableRes id: Int): Drawable? {
        try {
            var drawable: Drawable? = null
            try {
                drawable = ContextCompat.getDrawable(context, id)
            } catch (e: NotFoundException) {
                val vectorDrawableCompat = VectorDrawableCompat.create(context.resources, id, null)
                if (vectorDrawableCompat != null) {
                    drawable = vectorDrawableCompat.mutate()
                }
            }
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                return drawable
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    @JvmStatic
    fun getDrawable(context: Context, @DrawableRes id: Int, @ColorInt color: Int): Drawable? {
        try {
            var drawable: Drawable? = null
            try {
                drawable = ContextCompat.getDrawable(context, id)
            } catch (e: NotFoundException) {
                val vectorDrawableCompat = VectorDrawableCompat.create(context.resources, id, null)
                if (vectorDrawableCompat != null) {
                    drawable = vectorDrawableCompat.mutate()
                }
            }
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                return tintDrawable(drawable, color)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    @JvmStatic
    fun getDrawable(
        context: Context,
        @DrawableRes id: Int,
        width: Int,
        height: Int,
        @ColorInt color: Int
    ): Drawable? {
        try {
            var drawable: Drawable? = null
            try {
                drawable = ContextCompat.getDrawable(context, id)
            } catch (e: NotFoundException) {
                val vectorDrawableCompat = VectorDrawableCompat.create(context.resources, id, null)
                if (vectorDrawableCompat != null) {
                    drawable = vectorDrawableCompat.mutate()
                }
            }
            if (drawable != null) {
                drawable.setBounds(0, 0, width, height)
                return tintDrawable(drawable, color)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    @JvmStatic
    fun getVectorDrawable(context: Context, @DrawableRes id: Int, @ColorInt color: Int): Drawable? {
        return try {
            val vectorDrawableCompat = VectorDrawableCompat.create(context.resources, id, null)
            val drawable = vectorDrawableCompat!!.mutate()
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            tintDrawable(drawable, color)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @JvmStatic
    fun getVectorDrawable(
        context: Context,
        @DrawableRes id: Int,
        width: Int,
        height: Int,
        @ColorInt color: Int
    ): Drawable? {
        return try {
            val vectorDrawableCompat = VectorDrawableCompat.create(context.resources, id, null)
            val drawable = vectorDrawableCompat!!.mutate()
            drawable.setBounds(0, 0, width, height)
            tintDrawable(drawable, color)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @JvmStatic
    fun tintDrawable(drawable: Drawable?, tint: ColorStateList?): Drawable? {
        if (drawable == null) return null
        val wrappedDrawable = DrawableCompat.wrap(drawable).mutate()
        DrawableCompat.setTintList(wrappedDrawable, tint)
        return wrappedDrawable
    }

    @JvmStatic
    fun tintDrawable(drawable: Drawable?, @ColorInt color: Int): Drawable? {
        return tintDrawable(drawable, ColorStateList.valueOf(color))
    }

    @JvmStatic
    fun drawableToBitmap(drawable: Drawable?): Bitmap? {
        if (drawable == null) return null
        val width = drawable.intrinsicWidth
        val height = drawable.intrinsicHeight
        if (width <= 0 || height <= 0) return null
        return try {
            val config =
                if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
            val bitmap = Bitmap.createBitmap(width, height, config)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, width, height)
            drawable.draw(canvas)
            bitmap
        } catch (e: Throwable) {//oom?
            e.printStackTrace()
            null
        }
    }

    @JvmStatic
    fun drawableToBitmap(drawable: Drawable?, width: Int, height: Int): Bitmap? {
        if (drawable == null) return null
        if (width <= 0 || height <= 0) return null
        return try {
            val config =
                if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
            val bitmap = Bitmap.createBitmap(width, height, config)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, width, height)
            drawable.draw(canvas)
            bitmap
        } catch (e: Throwable) {//oom?
            e.printStackTrace()
            null
        }
    }

    @JvmStatic
    fun getBitmap(context: Context, @DrawableRes id: Int, @ColorInt color: Int): Bitmap? {
        return drawableToBitmap(getDrawable(context, id, color))
    }
}