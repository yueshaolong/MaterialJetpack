package com.ysl.materialjetpack.shizhan.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData

class ToolBarViewModel(application: Application) : BaseViewModel(application){
    val ivBack : MutableLiveData<Boolean> = MutableLiveData()
    val centerShow : MutableLiveData<Boolean> = MutableLiveData()
    val centerText : MutableLiveData<String> = MutableLiveData()
    val ivRight : MutableLiveData<Boolean> = MutableLiveData()
    val ivRight2 : MutableLiveData<Boolean> = MutableLiveData()

}