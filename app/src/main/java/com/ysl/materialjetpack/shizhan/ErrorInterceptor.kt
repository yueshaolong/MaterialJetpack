package com.ysl.materialjetpack.shizhan

import android.util.Log
import com.ysl.materialjetpack.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ErrorInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url: HttpUrl = request.url
        val requestUrl: String = url.toUrl().toString()
        val response: Response
        response = try {
            chain.proceed(request)
        } catch (e: Exception) {
            throw e
        }
        val code = response.code
        Log.d("intercept", "请求响应的code:$code")
        if (code != 200 && !BuildConfig.DEBUG) { //正式环境时上报错误
//            ErrorUtil.uploadError(requestUrl, code);
        }
        return response
    }
}