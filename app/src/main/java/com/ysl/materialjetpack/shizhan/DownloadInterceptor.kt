package com.ysl.materialjetpack.shizhan

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class DownloadInterceptor(private val downloadListener: ProgressListener?) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        return response.newBuilder()
                .body(ProgressResponseBody(response.body(), downloadListener))
                .build()
    }
}