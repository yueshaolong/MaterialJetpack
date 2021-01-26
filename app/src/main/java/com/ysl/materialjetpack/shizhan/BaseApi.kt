package com.ysl.materialjetpack.shizhan

import com.ysl.materialjetpack.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class BaseApi {
    companion object {
        fun <T,K> get(listener: ProgressListener?, clazz: Class<T>, clz : Class<K>) : T{
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
                    .baseUrl("http://39.104.137.131:9995/")
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(LiveDataCallAdapterFactory(clz))
                    .build()
                    .create(clazz)
        }
    }
}