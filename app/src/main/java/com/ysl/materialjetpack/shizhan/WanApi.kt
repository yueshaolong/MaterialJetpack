package com.ysl.materialjetpack.shizhan

import androidx.lifecycle.LiveData
import retrofit2.http.GET

interface WanApi {
    /**
     * 首页banner
     */
    @GET("banner/json")
    fun bannerList(): LiveData<ApiResponse<List<BannerVO>>>
}