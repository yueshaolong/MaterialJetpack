package com.ysl.materialjetpack.shizhan

import android.text.TextUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url()
        val requestUrl = url.toString()
        val newBuilder = request.newBuilder()
        if (!TextUtils.isEmpty(requestUrl) && !requestUrl.contains("login")) {
            newBuilder.header("token", "")
        }
        return chain.proceed(newBuilder.build())
    }
}