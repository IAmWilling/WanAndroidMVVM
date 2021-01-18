package com.zhy.wanandroidmvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zhy.wanandroidmvvm.R
import com.zhy.wanandroidmvvm.databinding.ArticleItemBinding
import com.zhy.wanandroidmvvm.model.DataX
import kotlinx.android.synthetic.main.article_item.view.*

class AllArticleAdapter(val context: Context) :
    PagingDataAdapter<DataX, RecyclerView.ViewHolder>(COMMPONS) {
    private var headerView: View? = null
    private val ITEM_HEADER = 0
    private val ITEM_BODY = 1

    companion object {
        @JvmStatic
        val COMMPONS = object : DiffUtil.ItemCallback<DataX>() {
            override fun areItemsTheSame(oldItem: DataX, newItem: DataX): Boolean =
                oldItem.id === newItem.id

            override fun areContentsTheSame(oldItem: DataX, newItem: DataX): Boolean =
                oldItem == newItem
        }
    }

    /**
     * 首页Adapter
     * 添加头布局
     */
    fun addHeaderItemView(headerView: View) {
        this.headerView = headerView
    }

    /**
     * 获取真实position
     */
    fun getRealPosition(p0: Int): Int = if (headerView == null) p0 else if(p0 == 0) 1 else p0 - 1



    override fun getItemViewType(position: Int): Int {
        if (headerView == null) {
            return ITEM_BODY
        } else {
            if(position != 0) {
                return ITEM_BODY
            }else {
                return ITEM_HEADER
            }

        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(getRealPosition(position))
        data?.let {
            when (holder) {
                is MyViewHolder -> {
                    holder.articleItemBinding?.apply {
                        datax = it
                    }
                }
                is HeaderViewHolder -> {
                    holder.itemView
                }
                else -> {

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_HEADER) {
            return HeaderViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_header_all_article_item,parent,false))
        } else if (viewType == ITEM_BODY) {
            return MyViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.article_item, parent, false
                )
            )
        } else {
            return MyViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.article_item, parent, false
                )
            )
        }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var articleItemBinding: ArticleItemBinding

        init {
            articleItemBinding = DataBindingUtil.bind(view)!!
        }
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {}


}