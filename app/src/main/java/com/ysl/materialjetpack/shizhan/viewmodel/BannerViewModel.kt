package com.ysl.materialjetpack.shizhan.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.ysl.materialjetpack.shizhan.api.WanApi
import com.ysl.materialjetpack.shizhan.http.HttpUtil
import com.ysl.materialjetpack.shizhan.model.BannerVO

class BannerViewModel constructor(application: Application) : BaseViewModel(application) {

    val where: MutableLiveData<List<String>> by lazy {
        val mutableListOf = mutableListOf("请求数据", "列表1", "列表2", "分页", "toolbar", "hilt")
        for (s in mutableListOf) {
            Log.i(TAG, "s=: $s")
        }

        repeat(3){//从0开始循环，循环3次
            Log.i(TAG, "i=: $it")
        }

        val d = mutableListOf<String>()
        for (i in 1..10) {
            if (i > 3) break
            Log.i(TAG, "i: $i")
            d.addAll(mutableListOf("请求数据","列表1","列表2","分页","toolbar","hilt"))
        }
        MutableLiveData(d)
    }

    var banner: MutableLiveData<List<BannerVO>> = MutableLiveData()

    fun loadBanner(isActivity: Boolean) {
        loading.value = TipsLoading(true, isActivity)
        handleData(isActivity, banner, HttpUtil.getData("https://www.wanandroid.com/", WanApi::class.java).bannerList())
    }

    fun click(view: View) {
        loadBanner(true)
    }

}