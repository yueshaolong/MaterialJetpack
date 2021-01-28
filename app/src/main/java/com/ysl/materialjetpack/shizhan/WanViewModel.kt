package com.ysl.materialjetpack.shizhan

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

class WanViewModel(application: Application) : BaseViewModel(application){

    val bannerList : LiveData<Result<List<BannerVO>>> = Transformations.switchMap(loading){
        val bannerList1 = HttpUtil.getData("https://www.wanandroid.com/", WanApi::class.java).bannerList()
        println("wer"+bannerList1.value)
        bannerList1
    }
    var banner: LiveData<List<BannerVO>>? =  Transformations.map(bannerList) {
        if (it.errorCode == -1){
            errorCode.value = bannerList.value?.errorCode
            message.value = bannerList.value?.message
            null
        }else if (it.data == null){
            empty.value = true
            null
        }else{
            it.data
        }
    }
//    fun getBanner(){
//        if (bannerList.value?.errorCode == -1){
//            errorCode.value = bannerList.value?.errorCode
//            message.value = bannerList.value?.message
//        }else if (bannerList.value?.data == null){
//            empty.value = true
//        }else{
//            banner = Transformations.map(bannerList) {
//                it.data
//            }
//        }
//    }

    fun loadData(){
        loading.value = true
    }

}