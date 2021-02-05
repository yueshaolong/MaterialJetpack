package com.ysl.materialjetpack.shizhan.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ysl.materialjetpack.shizhan.api.FileApi
import com.ysl.materialjetpack.shizhan.http.HttpUtil
import com.ysl.materialjetpack.shizhan.http.ProgressListener
import com.ysl.materialjetpack.shizhan.util.FileUtil
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class FileViewModel(application: Application) : BaseViewModel(application) {
    var progress = MutableLiveData<Int>()
        private set
    var downloadFile : MutableLiveData<File> =  MutableLiveData()
        private set

    fun downloadFile(isActivity: Boolean, fileName: String, fileId: String, dirName: String){
        loading.value = TipsLoading(true,isActivity)
        FileUtil.getFile(fileName, fileId, dirName,
                object : FileUtil.GetFileListener {
                    override fun success(file: File) {
                        loading.postValue(TipsLoading(false, isActivity))
                        progress.postValue(100)
                        downloadFile.postValue(file)
                    }

                    override fun error(e: Throwable) {
                        error.postValue(TipsThrowable(e, isActivity))
                    }

                    override fun download(file: File) {
                        download(file, object : FileUtil.DownloadListener {
                            override fun downloadFinish(file: File) {
                                downloadFile.postValue(file)
                            }

                            override fun downloadError(e: Throwable) {
                                error.postValue(TipsThrowable(e,isActivity))
                            }
                        }, HttpUtil.getData("http://39.104.137.131:9995/",
                                object : ProgressListener {
                                    override fun onProgress(currentPercent: Int) {
                                        Log.d("tag", "onProgress: $currentPercent")
                                        loading.postValue(TipsLoading(false, isActivity))
                                        progress.postValue(currentPercent)
                                    }

                                    override val isDownload: Boolean
                                        get() = true
                                }, FileApi::class.java).download())
                    }
                })
    }

    @SuppressLint("CheckResult")
    private fun download(file: File, listener: FileUtil.DownloadListener, observable: Observable<ResponseBody>){
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