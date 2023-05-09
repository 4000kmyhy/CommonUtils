package com.xu.commonutils.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * desc:
 **
 * user: xujj
 * time: 2023/2/2 18:03
 **/
abstract class BaseAdapter2<T>(data: MutableList<T>?) : RecyclerView.Adapter<BaseViewHolder2>() {

    protected var mData: MutableList<T>?

    init {
        mData = data
    }

    open fun setData(data: MutableList<T>?) {
        mData = data
        notifyDataSetChanged()
    }

    fun getData(): MutableList<T>? {
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

    fun remove(position: Int) {
        mData?.let {
            it.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, it.size - position)
        }
    }

    fun insert(position: Int, item: T) {
        mData?.let {
            if (position <= it.size) {
                it.add(position, item)
                notifyItemInserted(position)
                notifyItemRangeChanged(position, it.size - position)
            } else {//避免越界
                val position2 = it.size
                it.add(item)
                notifyItemInserted(position2)
                notifyItemRangeChanged(position2, it.size - position2)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder2 {
        val binding = getViewBinding(LayoutInflater.from(parent.context), parent, viewType)
        return BaseViewHolder2(binding)
    }

    abstract fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        viewType: Int
    ): ViewBinding

    abstract class OnItemClickCallback {

        open fun onItemClick(view: View, position: Int) {
        }

        open fun onItemLongClick(view: View, position: Int): Boolean {
            return false
        }

        open fun onItemChildClick(view: View, position: Int) {
        }
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(callback: OnItemClickCallback) {
        onItemClickCallback = callback
    }

    protected fun setOnViewClickListener(position: Int, vararg views: View?) {
        if (onItemClickCallback != null) {
            for (view in views) {
                view?.setOnClickListener { v -> onItemClickCallback?.onItemChildClick(v, position) }
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder2, position: Int) {
        val item = getItem(position)
        item?.let {
            onBindView(holder.binding, position, it)
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

    abstract fun onBindView(binding: ViewBinding, position: Int, item: T)

    override fun getItemCount(): Int {
        return getSize()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.layoutManager = getLayoutManager(recyclerView.context)
    }

    open fun getLayoutManager(context: Context): RecyclerView.LayoutManager {
        return LinearLayoutManager(context)
    }
}