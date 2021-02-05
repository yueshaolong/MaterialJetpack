package com.ysl.materialjetpack.shizhan.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.ysl.materialjetpack.shizhan.api.WanApi
import com.ysl.materialjetpack.shizhan.http.HttpUtil
import com.ysl.materialjetpack.shizhan.model.BannerVO

class BannerViewModel(application: Application) : BaseViewModel(application) {

    var banner: MutableLiveData<List<BannerVO>> = MutableLiveData()

    fun loadBanner(isActivity: Boolean) {
        loading.value = TipsLoading(true, isActivity)
        handleData(isActivity, banner, HttpUtil.getData("https://www.wanandroid.com/", WanApi::class.java).bannerList())
    }

    fun click(view: View) {
        loadBanner(true)
    }

}