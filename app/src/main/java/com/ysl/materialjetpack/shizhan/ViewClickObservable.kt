package com.ysl.materialjetpack.shizhan

import android.os.Looper
import android.view.View
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

open class ViewClickObservable(private val view: View) : Observable<Any?>() {
    override fun subscribeActual(observer: Observer<in Any?>) {
        if (!checkMainThread(observer)) {
            return
        }
        val listener = Listener(view, observer)
        observer.onSubscribe(listener)
        view.setOnClickListener(listener)
    }

    private fun checkMainThread(observer: Observer<in View>): Boolean {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            observer.onError(IllegalStateException(
                    "Expected to be called on the main thread but was " + Thread.currentThread().name))
            return false
        }
        return true
    }

    internal class Listener(private val view: View, private val observer: Observer<in View>)
        : MainThreadDisposable(), View.OnClickListener {
        override fun onClick(v: View) {
            if (!isDisposed) {
                observer.onNext(v)
            }
        }

        override fun onDispose() {
            view.setOnClickListener(null)
        }
    }
}