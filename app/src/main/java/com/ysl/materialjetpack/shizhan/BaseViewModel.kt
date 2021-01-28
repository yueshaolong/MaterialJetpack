package com.ysl.materialjetpack.shizhan

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    var loading = MutableLiveData<Boolean>()
        private set
    var errorCode = MutableLiveData<Int>()
        private set
    var empty = MutableLiveData<Boolean>()
        private set
    var message = MutableLiveData<String>()
        private set

    @SuppressLint("CheckResult")
    fun <T,R> a(ld: MutableLiveData<R>, observer: Observable<T>){
        observer.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
            object: Observer<T>{
                override fun onSubscribe(d: Disposable) {
                    CompositeDisposable().add(d)
                }

                override fun onNext(t: T) {//Result<data>->LiveData<data>
                    message.postValue("onNext")
                    if (t is Result<*>) {
                        val result = t as Result<*>
                        if (result.data == null ||(result.data is Collection<*> && (result.data as Collection<*>).size == 0)) {
                            message.postValue(result.message)
                        } else {
                            ld.postValue(t.data as R)
                        }
                    } else {
                        ld.postValue(t as R)
                    }
                }

                override fun onError(e: Throwable) {
                    message.postValue(e.message)
                }

                override fun onComplete() {
                    message.postValue("onComplete")
                }

            }
        }
    }
}