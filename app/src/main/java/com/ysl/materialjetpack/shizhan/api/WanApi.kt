package com.ysl.materialjetpack.shizhan.api

import com.ysl.materialjetpack.shizhan.model.Article
import com.ysl.materialjetpack.shizhan.model.BannerVO
import com.ysl.materialjetpack.shizhan.model.PageData
import com.ysl.materialjetpack.shizhan.model.Result
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface WanApi {
    //首页banner
    //https://www.wanandroid.com/banner/json
    @GET("banner/json")
    fun bannerList(): Observable<Result<List<BannerVO>>>

    //https://www.wanandroid.com/article/list/0/json
    @GET("article/list/{page}/json")
    fun articleList(@Path("page") page: Int): Observable<Result<PageData<List<Article>>>>

}