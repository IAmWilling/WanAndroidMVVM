package com.zhy.wanandroidmvvm

import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zhy.wanandroidmvvm.adapter.BottomTabNavAdapter
import com.zhy.wanandroidmvvm.base.BaseActivity
import com.zhy.wanandroidmvvm.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding>(),
    BottomNavigationView.OnNavigationItemSelectedListener {
    val fragmentList = listOf(AllArticleListFragment(), HotArticleListFragment())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewpager2.adapter =
            BottomTabNavAdapter(fragmentList, supportFragmentManager, lifecycle)
        binding.viewpager2.isUserInputEnabled = false
        binding.mBottomNavView.setOnNavigationItemSelectedListener(this)
    }

    override fun loadLayout(): Int = R.layout.activity_main

    override fun bindingData(viewDataBinding: ViewDataBinding) {
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.home -> binding.viewpager2.setCurrentItem(0, false)
            R.id.hot -> binding.viewpager2.setCurrentItem(1, false)
        }
        return true
    }


}