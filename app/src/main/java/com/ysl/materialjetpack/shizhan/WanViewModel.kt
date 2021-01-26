package com.ysl.materialjetpack.shizhan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import okhttp3.ResponseBody
import retrofit2.http.Body
import java.io.File

class WanViewModel : ViewModel(){
    private val tag = "WanViewModel"
    private val wanLiveData = MutableLiveData<Boolean>()

//    private val bannerList : LiveData<ApiResponse<List<BannerVO>>> =
//            Transformations.switchMap(wanLiveData) {
//                if (it) BaseApi.get(null, WanApi::class.java, ApiResponse::class.java).bannerList() else null
//    }
//    val banner : LiveData<List<BannerVO>> = Transformations.map(bannerList){
//        apiResponse -> apiResponse.data ?: ArrayList()
//    }

    fun loadData() {
        wanLiveData.value = true
    }


    val progressLiveData = MutableLiveData<Int>()
//    val liveData : LiveData<File> = MutableLiveData()
    val body : LiveData<ResponseBody> =
            Transformations.switchMap(wanLiveData) {
                BaseApi.get(object : ProgressListener{
                    override fun onProgress(currentPercent: Int) {
                        progressLiveData.postValue(currentPercent)
                    }
                    override val isDownload: Boolean
                        get() = true
                }, WanApi::class.java, ResponseBody::class.java).download()
            }


}