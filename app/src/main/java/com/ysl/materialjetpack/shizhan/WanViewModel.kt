package com.ysl.materialjetpack.shizhan

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

class WanViewModel : BaseViewModel<List<BannerVO>>(){
    private val tag = "WanViewModel"
    override fun requestData(): LiveData<ApiResponse<List<BannerVO>>> {
        return HttpUtil.getData("https://www.wanandroid.com/", WanApi::class.java).bannerList()
    }

    fun loadData(){
        baseLiveData.value = true
    }

    val banner : LiveData<List<BannerVO>> = Transformations.map(data){
        apiResponse -> apiResponse.data ?: ArrayList()
    }
}