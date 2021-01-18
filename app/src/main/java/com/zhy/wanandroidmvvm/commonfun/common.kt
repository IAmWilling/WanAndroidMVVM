package com.zhy.wanandroidmvvm.commonfun

import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttp
import okhttp3.Response
import java.io.IOException
import java.lang.Exception
import kotlin.coroutines.resumeWithException

//-----------------------公用函数-------------------------
/**
 * okhttp协程体
 */
inline suspend fun <reified T> okhttp3.Call.awaitResponse(): T {
    return withContext(Dispatchers.IO) {
        suspendCancellableCoroutine<T> {
            it.invokeOnCancellation {
                cancel()
            }
            enqueue(object : okhttp3.Callback {
                override fun onFailure(call: Call, e: IOException) {
                    it.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    response.body?.apply {
                        var json = string()
                        var data = Gson().jsonToAny<T>(json)
                        it.resume(data, null)
                    } ?: it.resumeWithException(Exception("time out"))

                }
            })
        }
    }
}

/**
 * gson直接解析泛型
 *
 * @param json
 */
inline fun <reified T> Gson.jsonToAny(json: String): T {
    return fromJson(json, T::class.java)
}

fun stringCheck(string: String): String = if (TextUtils.isEmpty(string)) "" else string

fun stringCheck(string: String, string2: String = ""): String {
    if (!TextUtils.isEmpty(string)) {
        return string
    } else if (!TextUtils.isEmpty(string2)) {
        return string2
    } else {
        return ""
    }
}


/**
 * 图片databinding绑定方法
 */
@BindingAdapter("imgPath")
fun imgPath(imageView: ImageView, pathUrl: String) {
    Glide.with(imageView)
        .load(if (TextUtils.isEmpty(pathUrl)) "https://www.wanandroid.com/resources/image/pc/default_project_img.jpg" else pathUrl)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(imageView)
}

