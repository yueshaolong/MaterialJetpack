package com.ysl.materialjetpack.shizhan.view.adapter

import android.util.Log
import androidx.paging.PagingSource
import com.ysl.materialjetpack.shizhan.api.WanApi
import com.ysl.materialjetpack.shizhan.http.ExceptionEngine
import com.ysl.materialjetpack.shizhan.http.HttpUtil
import com.ysl.materialjetpack.shizhan.model.Article
import com.ysl.materialjetpack.shizhan.model.PageData
import com.ysl.materialjetpack.shizhan.model.Result
import com.ysl.materialjetpack.shizhan.viewmodel.BaseViewModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class ArticlePagingSource(private val page : Int) : PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
         try {
            val currentLoadingPageKey = params.key ?: 1
            var responseData = mutableListOf<Article>()
            HttpUtil.getData("https://www.wanandroid.com/", WanApi::class.java)
                    .articleList(page)
                    .subscribe(object : Observer<Result<PageData<List<Article>>>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: Result<PageData<List<Article>>>) {
                    t.data?.let {
                        responseData = t.data!!.datas as MutableList<Article>
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d(BaseViewModel.TAG, "onError: $e")
                    ExceptionEngine.handleException(e)
                    throw Throwable(e)
//                    error.postValue(TipsThrowable(e, isActivity))
                }

                override fun onComplete() {
//                    loading.postValue(TipsLoading( false, isActivity))
                }

            })


             return LoadResult.Page(
                    data = responseData,
                    prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1,
                    nextKey = currentLoadingPageKey.plus(1)
            )
        }catch (e : Exception){
             return LoadResult.Error(e)
        }
    }
}