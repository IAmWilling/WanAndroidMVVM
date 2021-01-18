package com.zhy.wanandroidmvvm

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.zhy.wanandroidmvvm.adapter.AllArticleAdapter
import com.zhy.wanandroidmvvm.base.BaseFragment
import com.zhy.wanandroidmvvm.databinding.FragmentAllArticleList2Binding
import com.zhy.wanandroidmvvm.viewmodel.AllArticleViewModel
import kotlinx.android.synthetic.main.fragment_all_article_list2.*

class AllArticleListFragment : BaseFragment<FragmentAllArticleList2Binding>(),
    SwipeRefreshLayout.OnRefreshListener {
    lateinit var allArticleViewModel: AllArticleViewModel
    lateinit var adapter: AllArticleAdapter
    lateinit var skeletonScreen: SkeletonScreen
    override fun loadLayout(): Int = R.layout.fragment_all_article_list2

    override fun initView(root: View) {
        allArticleViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(AllArticleViewModel::class.java)

        context?.let {
            adapter = AllArticleAdapter(it)
            adapter.addHeaderItemView(layoutInflater.inflate(R.layout.adapter_header_all_article_item,null,false))
        }

        binding.mAllArticleRv.adapter = adapter
        binding.mAllArticleRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.swipeRefresh.setColorSchemeColors(Color.parseColor("#6200EE"))
        binding.swipeRefresh.setOnRefreshListener(this)
        binding.swipeRefresh.isRefreshing = true
//        skeletonScreen = Skeleton.bind(binding.mAllArticleRv)
//            .adapter(adapter)
//            .load(R.layout.article_item_skeleton)
//            .color(R.color.gujiacolor)
//            .duration(2000)
//            .show()
    }

    override fun initData() {
        allArticleViewModel.getAllArticleData().observe(this, Observer {
            it?.let {
                allArticleViewModel.startCoroutine { adapter.submitData(it) }

            }
        })
        allArticleViewModel.getAllArticlePageIsOne().observe(this, Observer {
            it?.let {
                if (it) {
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        })

    }

    override fun onRefresh() {
        adapter.refresh()
    }


}