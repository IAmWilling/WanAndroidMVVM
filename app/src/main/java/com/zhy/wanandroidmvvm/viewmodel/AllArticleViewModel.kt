package com.zhy.wanandroidmvvm.viewmodel

import androidx.lifecycle.*
import com.zhy.wanandroidmvvm.repository.AllArticleRepository
import kotlinx.coroutines.launch

class AllArticleViewModel : ViewModel() {
    val allArticleRepository by lazy {
        AllArticleRepository()
    }

    fun getAllArticleData() = allArticleRepository.getAllArticleLiveData()

    fun getAllArticlePageIsOne() = allArticleRepository.isOnePage

    /**
     * 载体
     * 运行协程体
     */
    fun startCoroutine(block: suspend () -> Unit) {
        viewModelScope.launch {
            block.invoke()
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}