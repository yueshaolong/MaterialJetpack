package com.ysl.materialjetpack.shizhan

interface UICallBack {
    fun showToast(toast: String)
    fun showError(throwable: Throwable)
    fun showEmpty()
}