package com.ysl.materialjetpack.shizhan.view

interface UICallBack {
    fun showToast(toast: String)
    fun showError(throwable: Throwable)
    fun showEmpty()
}