package com.ysl.materialjetpack.shizhan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    var loading = MutableLiveData<Boolean>()
        private set
    var errorCode = MutableLiveData<Int>()
        private set
    var empty = MutableLiveData<Int>()
        private set
    var message = MutableLiveData<String>()
        private set
}