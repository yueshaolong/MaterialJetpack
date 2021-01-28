package com.ysl.materialjetpack.shizhan

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import java.io.File

class FileViewModel(application: Application) : BaseViewModel(application) {
    var progress = MutableLiveData<Int>()
        private set
    var downloadFile : MutableLiveData<File> =  MutableLiveData()
        private set
    fun getFile(fileName: String, fileId: String, dirName: String){
        FileUtil.getFile(fileName, fileId, dirName,
                object : FileUtil.GetFileListener {
                    override fun success(file: File) {
                        downloadFile.postValue(file)
                    }
                    override fun error(e: Throwable) {
                        message.postValue(e.message)
                    }
                    override fun download(file: File) {
                        FileUtil.download(file, object :FileUtil.DownloadListener{
                            override fun downloadFinish(file: File) {
                                downloadFile.postValue(file)
                            }
                            override fun downloadError(e: Throwable) {
                                message.postValue(e.message)
                            }
                        }, HttpUtil.download("http://39.104.137.131:9995/",
                                object : ProgressListener {
                                    override fun onProgress(currentPercent: Int) {
                                        Log.d("tag", "onProgress: $currentPercent")
                                        progress.postValue(currentPercent)
                                    }
                                    override val isDownload: Boolean
                                        get() = true
                                }, WanApi::class.java).download())
                    }
                })
    }
}