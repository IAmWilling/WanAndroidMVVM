package com.zhy.wanandroidmvvm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity

/**
 * Created by zhy
 * MVVM activity基类
 */
abstract class BaseActivity<T : ViewDataBinding> : FragmentActivity() {
    //binding全局数据
    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //绑定DataBinding布局
        binding = DataBindingUtil.setContentView<T>(this, loadLayout())
        //设置绑定生命周期
        binding.lifecycleOwner = this
        //绑定ViewModel
        bindingData(binding)

    }

    //加载布局
    protected abstract fun loadLayout(): Int

    //绑定数据
    protected abstract fun bindingData(viewDataBinding: ViewDataBinding)

}