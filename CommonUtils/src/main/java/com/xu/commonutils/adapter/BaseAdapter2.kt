package com.xu.commonutils.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * desc:
 **
 * user: xujj
 * time: 2023/2/2 18:03
 **/
abstract class BaseAdapter2<T, K : BaseViewHolder>(data: List<T>?) : RecyclerView.Adapter<K>() {

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

    protected lateinit var binding: ViewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): K {
        binding = getViewBinding(LayoutInflater.from(parent.context), parent, viewType)
        return BaseViewHolder(binding.root) as K
    }

    abstract fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        viewType: Int
    ): ViewBinding

    private var onItemClickCallback: BaseAdapter.OnItemClickCallback? = null

    fun setOnItemClickCallback(callback: BaseAdapter.OnItemClickCallback) {
        onItemClickCallback = callback
    }

    protected fun setOnViewClickListener(position: Int, vararg views: View?) {
        if (onItemClickCallback != null) {
            for (view in views) {
                view?.setOnClickListener { v -> onItemClickCallback?.onItemChildClick(v, position) }
            }
        }
    }

    override fun onBindViewHolder(holder: K, position: Int) {
        val item = getItem(position)
        item?.let {
            onBindViewHolder(holder, position, it)
        }

        if (onItemClickCallback != null) {
            holder.itemView.setOnClickListener {
                onItemClickCallback?.onItemClick(it, position)
            }

            holder.itemView.setOnLongClickListener {
                onItemClickCallback?.onItemLongClick(it, position) == true
            }
        }
    }

    abstract fun onBindViewHolder(holder: K, position: Int, item: T)

    override fun getItemCount(): Int {
        return getSize()
    }
}