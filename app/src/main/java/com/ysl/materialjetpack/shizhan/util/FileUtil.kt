package com.ysl.materialjetpack.shizhan.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import com.blankj.utilcode.util.AppUtils
import java.io.File

/**
 * 创建文件类
 */
object FileUtil {
    private const val TAG = "FileUtil"

    /**
     * 获取图片目录
     */
    fun getPictureDirPath(context: Context?): File? {
        var mIVMSFolder: File? = null
        try {
            val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .absolutePath + File.separator + AppUtils.getAppName()
            mIVMSFolder = File(path)
            if (!mIVMSFolder.exists()) {
                mIVMSFolder.mkdirs()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mIVMSFolder
    }

    fun getLogDirPath(): File? {
            var mIVMSFolder: File? = null
            try {
                val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                        .absolutePath + File.separator + AppUtils.getAppName()
                mIVMSFolder = File(path)
                if (!mIVMSFolder.exists()) {
                    mIVMSFolder.mkdirs()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return mIVMSFolder
        }

    fun byte2FitMemorySize(byteNum: Long): String {
        return if (byteNum <= 0) {
            String.format("%sB", 0)
        } else if (byteNum < 1024) {
            String.format("%.2fB", byteNum.toDouble())
        } else if (byteNum < 1048576) {
            String.format("%.2fK", byteNum.toDouble() / 1024)
        } else if (byteNum < 1073741824) {
            String.format("%.2fM", byteNum.toDouble() / 1048576)
        } else {
            String.format("%.2fG", byteNum.toDouble() / 1073741824)
        }
    }

    fun createFile(fileName: String, dirName: String) : File? {
        if (TextUtils.isEmpty(fileName) || TextUtils.isEmpty(dirName)) return null
        val dir = File(getLogDirPath()?.absolutePath + File.separator + dirName)
        if (!dir.exists()) {
            Log.d(TAG, "createFile: 文件夹不存在，创建文件夹  $dir")
            dir.mkdirs()
        }
        val name : String
        if (fileName.lastIndexOf(".") >= 0 && fileName.length > 20) {
            name = MD5Util.getStringMd5(fileName)
                    .toString() + fileName.substring(fileName.lastIndexOf("."))
            Log.d(TAG, "createFile: $fileName")
        } else {
            name = fileName
        }
        val fileN = File(dir, name)
        Log.d(TAG, "创建缓存文件： $fileN")
        if (fileN.exists()) {
            if (fileN.length() > 0) {
                Log.d(TAG, "createFile: 文件存在，不用下载了")
            } else {
                Log.d(TAG, "createFile: 文件存在，但大小为0，删除文件")
                fileN.delete()
                fileN.createNewFile()
            }
        } else {
            fileN.createNewFile()
            Log.d(TAG, "createFile: 文件不存在，创建文件")
        }
        return fileN
    }

    @SuppressLint("CheckResult")
    fun getFile(fileName: String, fileId: String, dirName: String, listener: GetFileListener) {
        if (TextUtils.isEmpty(fileId) || TextUtils.isEmpty(fileName) || TextUtils.isEmpty(dirName)){
            listener.error(Throwable("参数为空"))
            Log.d(TAG, "getFile: 参数为空")
            return
        }
        val createFile : File? = createFile(fileName, dirName)
        if (createFile == null){
            listener.error(Throwable("创建文件失败"))
            return
        }
        val fileN: File = createFile
        if (fileN.exists() && fileN.length() > 0) {
            listener.success(fileN)
            return
        }
        listener.download(fileN)
    }

    interface GetFileListener {
        fun success(file: File)
        fun error(e: Throwable)
        fun download(file: File)
    }

//    @SuppressLint("CheckResult")
//    fun download(file: File, listener: DownloadListener, observable: Observable<ResponseBody>){
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe( object : Observer<ResponseBody>{
//                    override fun onSubscribe(d: Disposable) {
//                    }
//
//                    override fun onNext(responseBody: ResponseBody) {
//                        val buf = ByteArray(2048)
//                        var inputStream: InputStream = responseBody.byteStream()
//                        val outputStream = FileOutputStream(file)
//                        var len: Int
//                        try {
//                            inputStream = responseBody.byteStream()
//                            val total: Long = responseBody.contentLength()
//                            if (!file.exists()) {
//                                file.createNewFile()
//                            }
//                            var sum: Long = 0
//                            while (inputStream.read(buf).also { len = it } != -1) {
//                                outputStream.write(buf, 0, len)
//                                sum += len.toLong()
//                                val progress = (sum * 1.0f / total * 100).toInt()
//                                Log.d("------?", "写入缓存文件${file.getName()}进度: $progress")
//                            }
//                            outputStream.flush()
//                            Log.d("------?", "文件下载成功,准备展示文件。")
//                            listener.downloadFinish(file)
//                        } catch (e: Exception) {
//                            Log.d("------?", "onNext文件下载异常 = $e")
//                            listener.downloadError(e)
//                        } finally {
//                            try {
//                                inputStream.close()
//                            } catch (e: IOException) {
//                            }
//                            try {
//                                outputStream.close()
//                            } catch (e: IOException) {
//                                e.printStackTrace()
//                            }
//                        }
//                    }
//
//                    override fun onError(e: Throwable) {
//                        Log.d("------?", "onError文件下载异常 = $e")
//                        listener.downloadError(e)
//                    }
//
//                    override fun onComplete() {
//
//                    }
//
//                })
//    }

    interface DownloadListener {
        fun downloadFinish(file: File)
        fun downloadError(e: Throwable)
    }
}

