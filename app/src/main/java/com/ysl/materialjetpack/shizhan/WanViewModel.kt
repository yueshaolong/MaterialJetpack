package com.ysl.materialjetpack.shizhan

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.MutableLiveData

class WanViewModel(application: Application) : BaseViewModel(application){

    var banner: MutableLiveData<List<BannerVO>> =  MutableLiveData()

    @SuppressLint("CheckResult")
    fun loadData(){
        loading.value = true
        handleData(banner, HttpUtil.getData("https://www.wanandroid.com/", WanApi::class.java).bannerList())
    }

}