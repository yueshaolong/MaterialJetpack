package com.ysl.materialjetpack.shizhan.view.adapter

import androidx.paging.PagingSource
import com.ysl.materialjetpack.shizhan.api.WanApi
import com.ysl.materialjetpack.shizhan.http.HttpUtil
import com.ysl.materialjetpack.shizhan.model.Article

class ArticlePagingSource(private val page : Int) : PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val articleList = HttpUtil.getData("https://www.wanandroid.com/", WanApi::class.java)
                    .articleList(page)
                    .map {
                        it.data?.datas
                    }
            LoadResult.Page(
                    data = articleList as List<*>,
                    prevKey = articleList.firstOrNull()?.id,
                    nextKey = articleList.lastOrNull()?.id
            )
        }catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}