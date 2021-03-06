package com.ysl.materialjetpack.shizhan.http

import com.ysl.materialjetpack.shizhan.http.ProgressListener
import com.ysl.materialjetpack.shizhan.http.ProgressRequestBody
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class UploadInterceptor(private val listener: ProgressListener?) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val build = request.newBuilder().post(ProgressRequestBody(request.body(), listener)).build()
        return chain.proceed(build)
    }
}