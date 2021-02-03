package com.ysl.materialjetpack.shizhan.http

interface ProgressListener {
    fun onProgress(currentPercent: Int)
    //返回true表示下载，false表示上传
    val isDownload: Boolean
}