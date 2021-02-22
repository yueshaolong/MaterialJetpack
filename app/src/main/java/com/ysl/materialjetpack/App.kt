package com.ysl.materialjetpack

import android.app.Application
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

    }
}