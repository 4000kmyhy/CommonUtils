package com.xu.commonutils.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Modifier
import java.lang.reflect.ParameterizedType

/**
 * desc:
 **
 * user: xujj
 * time: 2022/8/16 14:39
 **/
abstract class BaseAdapter<T, K : BaseViewHolder?>(data: List<T>?) : RecyclerView.Adapter<K?>() {

    protected var mData: List<T>?

    init {
        mData = data
    }

    fun setData(data: List<T>?) {
        mData = data
        notifyDataSetChanged()
    }

    fun getData(): List<T>? {
        return mData
    }

    fun getItem(position: Int): T? {
        return if (mData != null && position >= 0 && position < mData!!.size) {
            mData!![position]
        } else {
            null
        }
    }

    fun getSize(): Int {
        return if (mData != null) mData!!.size else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): K {
        val view = LayoutInflater.from(parent.context).inflate(getLayoutId(viewType), parent, false)
        return getViewHolder(view)
    }

    @LayoutRes
    protected abstract fun getLayoutId(viewType: Int): Int

    private fun getViewHolder(view: View): K {
        var temp: Class<*>? = javaClass
        var z: Class<*>? = null
        while (z == null && null != temp) {
            z = getInstancedGenericKClass(temp)
            temp = temp.superclass
        }
        val k: K?
        // 泛型擦除会导致z为null
        k = z?.let { createGenericKInstance(it, view) } ?: BaseViewHolder(view) as K
        return k ?: BaseViewHolder(view) as K
    }

    private fun getInstancedGenericKClass(z: Class<*>): Class<*>? {
        val type = z.genericSuperclass
        if (type is ParameterizedType) {
            val types = type.actualTypeArguments
            for (temp in types) {
                if (temp is Class<*>) {
                    val tempClass = temp
                    if (BaseViewHolder::class.java.isAssignableFrom(tempClass)) {
                        return tempClass
                    }
                } else if (temp is ParameterizedType) {
                    val rawType = temp.rawType
                    if (rawType is Class<*> && BaseViewHolder::class.java.isAssignableFrom(
                            rawType
                        )
                    ) {
                        return rawType
                    }
                }
            }
        }
        return null
    }

    private fun createGenericKInstance(z: Class<*>, view: View): K? {
        try {
            val constructor: Constructor<*>
            // inner and unstatic class
            return if (z.isMemberClass && !Modifier.isStatic(z.modifiers)) {
                constructor = z.getDeclaredConstructor(javaClass, View::class.java)
                constructor.isAccessible = true
                constructor.newInstance(this, view) as K
            } else {
                constructor = z.getDeclaredConstructor(View::class.java)
                constructor.isAccessible = true
                constructor.newInstance(view) as K
            }
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return null
    }

    override fun onBindViewHolder(holder: K, position: Int) {
        val item = getItem(position)
        item?.let { onBindViewHolder(holder, position, it) }
    }

    abstract fun onBindViewHolder(holder: K, position: Int, item: T)

    override fun getItemCount(): Int {
        return getSize()
    }
}
