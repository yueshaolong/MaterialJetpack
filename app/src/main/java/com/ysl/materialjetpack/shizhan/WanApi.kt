package com.ysl.materialjetpack.shizhan

import io.reactivex.Observable
import retrofit2.http.GET

interface WanApi {
    //首页banner
    //https://www.wanandroid.com/banner/json
    @GET("banner/json")
    fun bannerList(): Observable<Result<List<BannerVO>>>

}