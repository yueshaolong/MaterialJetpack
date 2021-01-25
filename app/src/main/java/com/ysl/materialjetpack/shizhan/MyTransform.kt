//package com.ysl.materialjetpack.shizhan
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.Transformations
//
//class MyTransform {
//    private val refreshTrigger = MutableLiveData<Boolean>()
//    private val api : T = BaseApi.get()<T>
//    private val bannerLis : LiveData<ApiResponse<List<BannerVO>>> = Transformations.switchMap(refreshTrigger) {
//        //当refreshTrigger的值被设置时，bannerList
//        api.bannerList()
//    }
//}