package com.ysl.materialjetpack.shizhan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.io.File

abstract class BaseViewModel<T> : ViewModel(){
    private val tag = "BaseViewModel"
    protected val baseLiveData = MutableLiveData<Boolean>()
    protected val data : LiveData<ApiResponse<T>> =
            Transformations.switchMap(baseLiveData) {
                if (it) requestData() else null
            }
    protected abstract fun requestData(): LiveData<ApiResponse<T>>
    val errorCode : LiveData<Int> = Transformations.map(data){
        it.errorCode
    }
    val message : LiveData<String> = Transformations.map(data){
        it.message
    }


    val error = MutableLiveData<String>()
    val progress = MutableLiveData<Int>()
    val downloadFile : MutableLiveData<File> =  MutableLiveData()
    fun getFile(fileName: String, fileId: String, dirName: String){
        FileUtil.getFile(fileName, fileId, dirName,
                object : FileUtil.GetFileListener {
                    override fun success(file: File) {
                        downloadFile.postValue(file)
                    }
                    override fun error(e: Throwable) {
                        error.postValue(e.message)
                    }
                    override fun download(file: File) {
                        FileUtil.download(file, object :FileUtil.DownloadListener{
                            override fun downloadFinish(file: File) {
                                downloadFile.postValue(file)
                            }
                            override fun downloadError(e: Throwable) {
                                error.postValue(e.message)
                            }
                        }, HttpUtil.download("http://39.104.137.131:9995/",
                                object : ProgressListener {
                                    override fun onProgress(currentPercent: Int) {
                                        Log.d(tag, "onProgress: $currentPercent")
                                        progress.postValue(currentPercent)
                                    }
                                    override val isDownload: Boolean
                                        get() = true
                                }, WanApi::class.java).download())
                    }
                })
    }
}