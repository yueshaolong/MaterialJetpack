package com.ysl.materialjetpack.shizhan

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.internal.operators.observable.ObservableOnErrorNext
import java.util.function.Consumer

class WanViewModel(application: Application) : BaseViewModel(application){

//    private val bannerList = Transformations.switchMap(loading) {
//
//    }
@SuppressLint("CheckResult")
fun d(){
        HttpUtil.getData("https://www.wanandroid.com/", WanApi::class.java)
                .bannerList()
                .subscribe()
    }
    val banner : LiveData<List<BannerVO>> = Transformations.map(bannerList){
        it.data
    }


    fun loadData(){
        loading.value = true
    }


}