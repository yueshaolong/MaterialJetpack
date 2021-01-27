package com.ysl.materialjetpack

import android.app.Application
import com.blankj.utilcode.util.Utils

//@HiltAndroidApp
class App : Application(){

    override fun onCreate() {
        super.onCreate()

        //utils初始化
        Utils.init(this)
    }
}