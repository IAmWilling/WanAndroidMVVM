package com.zhy.wanandroidmvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    lateinit var binding: T
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, loadLayout(), container, false)
        binding.lifecycleOwner = this
        initView(binding.root)
        initData()
        return binding.root
    }

    protected abstract fun loadLayout(): Int
    protected abstract fun initView(root: View)
    protected abstract fun initData()
}