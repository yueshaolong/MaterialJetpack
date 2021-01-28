package com.ysl.materialjetpack.shizhan

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapter<T>(private val responseType: Type) :CallAdapter<T, LiveData<T>>{
    override fun adapt(call: Call<T>): LiveData<T> {
        return object : LiveData<T>(){
            override fun onActive() {
                super.onActive()
                val started = AtomicBoolean(false)
                if (started.compareAndSet(false, true)) {//确保执行一次
                    call.enqueue(object : Callback<T> {
                        override fun onFailure(call: Call<T>, t: Throwable) {
                            val value = Result<T>(null, -1, t.message) as T
                            postValue(value)
                        }

                        override fun onResponse(call: Call<T>, response: Response<T>) {
                            postValue(response.body())
                        }
                    })
                }
            }
        }
    }

    override fun responseType(): Type {
        return responseType
    }
}