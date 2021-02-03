package com.ysl.materialjetpack.shizhan.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.ysl.materialjetpack.shizhan.api.WanApi
import com.ysl.materialjetpack.shizhan.http.HttpUtil
import com.ysl.materialjetpack.shizhan.model.BannerVO

class WanViewModel(application: Application) : BaseViewModel(application){

    var banner: MutableLiveData<List<BannerVO>> =  MutableLiveData()

    @SuppressLint("CheckResult")
    fun loadData(){
        loading.value = true
        handleData(banner, HttpUtil.getData("https://www.wanandroid.com/", WanApi::class.java).bannerList())
    }

    fun click(view: View){
        loadData()
    }
}