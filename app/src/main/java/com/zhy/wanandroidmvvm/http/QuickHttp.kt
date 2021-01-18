package com.zhy.wanandroidmvvm.http

import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit
import com.zhy.wanandroidmvvm.commonfun.awaitResponse

/**
 * Created by zhy
 * 协程快速Http
 */
object QuickHttp {
    @JvmStatic
    var okHttpClient: OkHttpClient

    init {
        okHttpClient = OkHttpClient.Builder().apply {
            connectTimeout(15, TimeUnit.SECONDS)
            writeTimeout(20, TimeUnit.SECONDS)
            readTimeout(20, TimeUnit.SECONDS)
        }.build()
    }

    /**
     * 协程体get
     */
    suspend inline fun <reified T> get(url: String): T {
        var request = Request.Builder()
            .url(url)
            .get()
            .build()
        return okHttpClient.newCall(request).awaitResponse<T>()
    }


}