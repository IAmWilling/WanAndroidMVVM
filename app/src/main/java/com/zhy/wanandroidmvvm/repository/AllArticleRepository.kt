package com.zhy.wanandroidmvvm.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.zhy.wanandroidmvvm.http.QuickHttp
import com.zhy.wanandroidmvvm.model.ArticleResponse
import com.zhy.wanandroidmvvm.model.DataX
import kotlinx.coroutines.delay
import java.lang.Exception

class AllArticleRepository {
    var isOnePage = MutableLiveData<Boolean>(false)

    //获取当前DataSource livedata
    fun getAllArticleLiveData() = Pager(PagingConfig(pageSize = 20, prefetchDistance = 15)) {
        AllArticleDataSource()
    }.flow.asLiveData()


    /**
     * DataSource
     */
    private inner class AllArticleDataSource : PagingSource<Int, DataX>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataX> {
            return try {
                val page = params.key ?: 0

                val (curPage, data, pageCount) = QuickHttp.get<ArticleResponse>("https://www.wanandroid.com/article/list/$page/json").data
                isOnePage.postValue(page == 0)
                LoadResult.Page(
                    prevKey = null,
                    data = data,
                    nextKey = if (curPage == pageCount) null else curPage + 1
                )
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }

    }
}