package com.ysl.materialjetpack.shizhan.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ysl.materialjetpack.shizhan.http.ExceptionEngine
import com.ysl.materialjetpack.shizhan.model.Result
import com.ysl.materialjetpack.shizhan.util.FileUtil
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    companion object{
        const val TAG = "BaseViewModel"
    }
    var loading = MutableLiveData<Boolean>()
        private set
    var empty = MutableLiveData<Boolean>()
        private set
    var error = MutableLiveData<Throwable>()
        private set
    val mDisposable: CompositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    fun <T, R> handleData(ld: MutableLiveData<R>, observable: Observable<T>){
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object : Observer<T> {
                            override fun onSubscribe(d: Disposable) {
                                mDisposable.add(d)
                            }

                            override fun onNext(t: T) {
                                if (t is Result<*>) {
                                    val result = t as Result<*>
                                    if (result.data == null || (result.data is Collection<*> && (result.data as Collection<*>).size == 0)) {
                                        error.postValue(Throwable(result.errorMsg))
                                    } else {
                                        ld.postValue(t.data as R)
                                    }
                                } else {
                                    ld.postValue(t as R)
                                }
                            }

                            override fun onError(e: Throwable) {
                                Log.d(TAG, "onError: $e")
                                ExceptionEngine.handleException(e)
                                error.postValue(e)
                            }

                            override fun onComplete() {
                                loading.postValue(false)
                            }

                        }
                )
    }

    @SuppressLint("CheckResult")
    fun download(file: File, listener: FileUtil.DownloadListener, observable: Observable<ResponseBody>){
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseBody> {
                    override fun onSubscribe(d: Disposable) {
                        mDisposable.add(d)
                    }

                    override fun onNext(responseBody: ResponseBody) {
                        val buf = ByteArray(2048)
                        var inputStream: InputStream = responseBody.byteStream()
                        val outputStream = FileOutputStream(file)
                        var len: Int
                        try {
                            inputStream = responseBody.byteStream()
                            val total: Long = responseBody.contentLength()
                            if (!file.exists()) {
                                file.createNewFile()
                            }
                            var sum: Long = 0
                            while (inputStream.read(buf).also { len = it } != -1) {
                                outputStream.write(buf, 0, len)
                                sum += len.toLong()
                                val progress = (sum * 1.0f / total * 100).toInt()
                                Log.d("------?", "写入缓存文件${file.getName()}进度: $progress")
                            }
                            outputStream.flush()
                            Log.d("------?", "文件下载成功,准备展示文件。")
                            listener.downloadFinish(file)
                        } catch (e: Exception) {
                            Log.d("------?", "onNext文件下载异常 = $e")
                            listener.downloadError(e)
                        } finally {
                            try {
                                inputStream.close()
                            } catch (e: IOException) {
                            }
                            try {
                                outputStream.close()
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        Log.d("------?", "onError文件下载异常 = $e")
                        listener.downloadError(e)
                    }

                    override fun onComplete() {

                    }
                })
    }
}