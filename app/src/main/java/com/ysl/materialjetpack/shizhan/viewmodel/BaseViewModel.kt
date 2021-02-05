package com.ysl.materialjetpack.shizhan.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ysl.materialjetpack.shizhan.http.ExceptionEngine
import com.ysl.materialjetpack.shizhan.model.Result
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

data class TipsLoading(var isLoading: Boolean, var isActivity: Boolean)
data class TipsEmpty(var isActivity: Boolean)
data class TipsThrowable(var throwable: Throwable, var isActivity: Boolean)

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    companion object{
        const val TAG = "BaseViewModel"
    }
    var loading = MutableLiveData<TipsLoading>()
        private set
    var empty = MutableLiveData<TipsEmpty>()
        private set
    var error = MutableLiveData<TipsThrowable>()
        private set
    val mDisposable: CompositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    fun <T, R> handleData(isActivity: Boolean, ld: MutableLiveData<R>, observable: Observable<T>){
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object : Observer<T> {
                            override fun onSubscribe(d: Disposable) {
                                mDisposable.add(d)
                            }

                            override fun onNext(t: T) {
                                if (t is Result<*>) {
                                    val result = t as Result<*>
                                    if (result.data == null || (result.data is Collection<*> && (result.data as Collection<*>).size == 0)) {
                                        empty.postValue(TipsEmpty(isActivity))
                                    } else {
                                        ld.postValue(t.data as R)
                                    }
                                } else {
                                    ld.postValue(t as R)
                                }
                            }

                            override fun onError(e: Throwable) {
                                Log.d(TAG, "onError: $e")
                                ExceptionEngine.handleException(e)
                                error.postValue(TipsThrowable(e, isActivity))
                            }

                            override fun onComplete() {
                                loading.postValue(TipsLoading( false, isActivity))
                            }

                        }
                )
    }
}