package com.ysl.materialjetpack

import android.app.Application
import cn.jiguang.share.android.api.JShareInterface
import cn.jiguang.share.android.api.PlatformConfig
import com.blankj.utilcode.util.Utils
import com.kingja.loadsir.core.LoadSir
import com.ysl.loadsirlibrary.EmptyCallback
import com.ysl.loadsirlibrary.ErrorCallback
import com.ysl.loadsirlibrary.LoadingCallback
import dagger.hilt.android.HiltAndroidApp

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
//                .setQQ("1106011004", "YIbPvONmBQBZUGaN")
//                .setSinaWeibo("374535501", "baccd12c166f1df96736b51ffbf600a2", "https://www.jiguang.cn")
//                .setFacebook("1847959632183996", "JShareDemo")
//                .setTwitter("fCm4SUcgYI1wUACGxB2erX5pL", "NAhzwYCgm15FBILWqXYDKxpryiuDlEQWZ5YERnO1D89VBtZO6q")
//                .setJchatPro("1847959632183996")
        JShareInterface.init(this, platformConfig)
    }
}