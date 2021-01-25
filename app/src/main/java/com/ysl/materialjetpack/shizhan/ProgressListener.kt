package com.ysl.materialjetpack.shizhan

interface ProgressListener {

    fun onProgress(currentLength: Int)
    //返回true表示下载，false表示上传
    val isDownload: Boolean

}