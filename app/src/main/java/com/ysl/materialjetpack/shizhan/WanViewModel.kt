package com.ysl.materialjetpack.shizhan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import javax.xml.transform.Transformer

class WanViewModel : ViewModel(){
    private val wanLiveData = MutableLiveData<Boolean>()
    private val bannerList : LiveData<ApiResponse<List<BannerVO>>> = Transformations.switchMap(wanLiveData) {
        BaseApi.get(null, WanApi::class.java).bannerList()
    }

    val banner : LiveData<List<BannerVO>> = Transformations.map(bannerList){
        apiResponse -> apiResponse.data ?: ArrayList()
    }

    fun loadData() {
        wanLiveData.value = true
    }
}