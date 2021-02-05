package com.ysl.materialjetpack.shizhan.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ysl.materialjetpack.shizhan.api.WanApi
import com.ysl.materialjetpack.shizhan.http.HttpUtil
import com.ysl.materialjetpack.shizhan.model.Article
import com.ysl.materialjetpack.shizhan.model.PageData

class ArticleViewModel(application: Application) : BaseViewModel(application) {

    var article: MutableLiveData<PageData<List<Article>>> = MutableLiveData()

    fun loadArticle(isActivity: Boolean, page: Int, showLoading: Boolean){
        Log.d(TAG, "loadArticle: $page")
        if (page == 0 && showLoading) {
            loading.value = TipsLoading(true, isActivity)
        }
        handleData(isActivity, article, HttpUtil.getData("https://www.wanandroid.com/", WanApi::class.java).articleList(page))
    }

}