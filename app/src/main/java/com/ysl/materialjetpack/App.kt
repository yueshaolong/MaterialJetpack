package com.ysl.materialjetpack

import android.app.Application
import android.os.Environment
import cn.jiguang.share.android.api.JShareInterface
import cn.jiguang.share.android.api.PlatformConfig
import com.blankj.utilcode.util.Utils
import com.kingja.loadsir.core.LoadSir
import com.ysl.loadsirlibrary.EmptyCallback
import com.ysl.loadsirlibrary.ErrorCallback
import com.ysl.loadsirlibrary.LoadingCallback
import dagger.hilt.android.HiltAndroidApp
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@HiltAndroidApp
class App : Application(){

    override fun onCreate() {
        super.onCreate()

        //utils初始化
        Utils.init(this)

        //loadsir设置缺省页
        LoadSir.beginBuilder()
                .addCallback(LoadingCallback())//添加各种状态页
                .addCallback(EmptyCallback())
                .addCallback(ErrorCallback())
                .setDefaultCallback(LoadingCallback::class.java) //设置默认状态页
                .commit()

        JShareInterface.setDebugMode(true)
        val platformConfig = PlatformConfig()
                .setWechat("wxc40e16f3ba6ebabc", "dcad950cd0633a27e353477c4ec12e7a")//TODO
                .setQQ("1111493049", "ofKWNzjtci0ev4r5")
                .setSinaWeibo("374535501", "baccd12c166f1df96736b51ffbf600a2", "https://www.jiguang.cn")
                .setFacebook("1847959632183996", "JShareDemo")
                .setTwitter("fCm4SUcgYI1wUACGxB2erX5pL", "NAhzwYCgm15FBILWqXYDKxpryiuDlEQWZ5YERnO1D89VBtZO6q")
                .setJchatPro("1847959632183996")
        JShareInterface.init(this, platformConfig)

        /*/
        JShareInterface.init(this);
       / **/object : Thread() {
            override fun run() {
                val imageFile: File? = copyResurces("error.jpg", "test_img.jpg", 0)
                if (imageFile != null) {
                    ImagePath = imageFile.absolutePath
                }
                super.run()
            }
        }.start()
    }
    private fun copyResurces(src: String, dest: String, flag: Int): File? {
        var filesDir: File? = null
        try {
            if (flag == 0) { //copy to sdcard
                filesDir = File(Environment.getExternalStorageDirectory().absoluteFile.toString()
                        + "/jiguang/" + dest)
                val parentDir = filesDir.parentFile
                if (!parentDir.exists()) {
                    parentDir.mkdirs()
                }
            } else { //copy to data
                filesDir = File(getFilesDir(), dest)
            }
            if (!filesDir.exists()) {
                filesDir.createNewFile()
                val open = assets.open(src)
                val fileOutputStream = FileOutputStream(filesDir)
                val buffer = ByteArray(4 * 1024)
                var len = 0
                while (open.read(buffer).also { len = it } != -1) {
                    fileOutputStream.write(buffer, 0, len)
                }
                open.close()
                fileOutputStream.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            if (flag == 0) {
                filesDir = copyResurces(src, dest, 1)
            }
        }
        return filesDir
    }
    companion object{
        var ImagePath = ""
    }
}