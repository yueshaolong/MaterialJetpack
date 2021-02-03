package com.ysl.materialjetpack.shizhan.api

import com.ysl.materialjetpack.shizhan.model.BannerVO
import com.ysl.materialjetpack.shizhan.model.Result
import io.reactivex.Observable
import retrofit2.http.GET

interface WanApi {
    //首页banner
    //https://www.wanandroid.com/banner/json
    @GET("banner/json")
    fun bannerList(): Observable<Result<List<BannerVO>>>

}