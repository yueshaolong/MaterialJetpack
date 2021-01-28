package com.ysl.materialjetpack.shizhan

import androidx.lifecycle.LiveData
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET

interface WanApi {
    //首页banner
    //https://www.wanandroid.com/banner/json
    @GET("banner/json")
    fun bannerList(): LiveData<Result<List<BannerVO>>>

    //http://39.104.137.131:9995/versions/files/NewBlueSky_xsbn/image-d
    @GET("versions/files/NewBlueSky_xsbn/image-d")
    fun download() : Observable<ResponseBody>
}