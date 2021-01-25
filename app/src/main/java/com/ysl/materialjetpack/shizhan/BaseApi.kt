package com.ysl.materialjetpack.shizhan

import com.ysl.materialjetpack.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class BaseApi {
    companion object {
        fun <T> get(listener: ProgressListener?, clazz: Class<T>) : T{
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
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(loggingInterceptor)
            }
            return Retrofit.Builder()
                    .baseUrl("https://www.wanandroid.com/")
                    .client(builder.build())
                    .addCallAdapterFactory(LiveDataCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(clazz)
        }
    }
}