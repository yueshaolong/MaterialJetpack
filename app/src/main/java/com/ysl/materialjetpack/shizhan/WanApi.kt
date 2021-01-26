package com.ysl.materialjetpack.shizhan

import androidx.lifecycle.LiveData
import okhttp3.ResponseBody
import retrofit2.http.GET

interface WanApi {
    /**
     * 首页banner
     */
    @GET("banner/json")
    fun bannerList(): LiveData<ApiResponse<List<BannerVO>>>

    //http://39.104.137.131:9995/versions/files/NewBlueSky_xsbn/image-d
    @GET("versions/files/NewBlueSky_xsbn/image-d")
    fun download() : LiveData<ResponseBody>
}