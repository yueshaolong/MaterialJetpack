package com.ysl.materialjetpack.shizhan.http

import com.ysl.materialjetpack.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HttpUtil {
    companion object {
        fun <T> getData(baseUrl: String, clazz: Class<T>) : T{
            return getData(baseUrl, null, clazz)
        }

        fun <T> getData(baseUrl: String, listener: ProgressListener?, clazz: Class<T>) : T{
            val builder = OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
            if (listener != null) {
                if (listener.isDownload) {
                    builder.addInterceptor(DownloadInterceptor(listener))
                } else {
                    builder.addInterceptor(UploadInterceptor(listener))
                }
            }
            builder.addInterceptor(ErrorInterceptor())
            builder.addInterceptor(HeaderInterceptor())
            if (BuildConfig.DEBUG && listener == null) {//如果想监听进度，不能设置log拦截器
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(loggingInterceptor)
            }
            return Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(clazz)
        }
    }
}