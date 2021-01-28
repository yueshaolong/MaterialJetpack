package com.ysl.materialjetpack.shizhan;

import android.text.TextUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class MyObserver<T> implements Observer<T> {
//    private static final Logger logger = Logger.getLogger("MyObserver");
//    private CompositeDisposable compositeDisposable;

//    public MyObserver(CompositeDisposable compositeDisposable) {
//        this.compositeDisposable = compositeDisposable;
//    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {
        compositeDisposable.add(d);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if(e.getMessage() != null && !TextUtils.isEmpty(e.getMessage()) && e.getMessage().contains("401 Unauthorized")){
//            MyFunc.login();
        }else {
//            setError(mPresenterView, BuildConfig.DEBUG ? e.getMessage() :
//                    ExceptionEngine.handleException(e).message, 0);
        }
    }

    @Override
    public void onNext(T t) {
//        if (mPresenterView != null) {
//            handleData(t);
//        }
    }

    public abstract void handleData(T t);

//    public static void setError(BaseView mPresenterView, String message, int errorCode){
//        if (mPresenterView == null) return;
//        mPresenterView.showError(new ErrorBundle() {
//            @Override
//            public Exception getException() {
//                return null;
//            }
//
//            @Override
//            public String getErrorMessage() {
//                return message;
//            }
//
//            @Override
//            public int getStateCode() {
//                return errorCode;
//            }
//        });
//    }

//    public static void setToast(BaseView mPresenterView, String message){
//        if (mPresenterView == null) return;
//        mPresenterView.showToast(message);
//    }
}
