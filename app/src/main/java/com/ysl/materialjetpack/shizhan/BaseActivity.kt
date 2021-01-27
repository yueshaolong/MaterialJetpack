package com.ysl.materialjetpack.shizhan

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.KeyboardUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import com.ysl.materialjetpack.databinding.ActBaseBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import xyz.bboylin.universialtoast.UniversalToast
import java.util.concurrent.TimeUnit

abstract class BaseActivity<T> : AppCompatActivity(),UICallBack {
    private val tag = "BaseActivity"
    private lateinit var binding: ActBaseBinding
    private val viewModel : BaseViewModel<T> by viewModels()

    protected lateinit var mActivity : Activity
    protected lateinit var imm : InputMethodManager
    protected lateinit var layoutView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE) //设置无ActionBar
        mActivity = this
        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        initVariables()
        binding = ActBaseBinding.inflate(layoutInflater)
        addContent()
        setContentView(binding.root)
        initViews(savedInstanceState)
        initEvent()
        requestPermission()
    }
    private fun addContent() {
        layoutView = View.inflate(this, getLayoutId(), null)
        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT)
        binding.flContent.addView(layoutView, params)
    }
    abstract fun initVariables()
    abstract fun getLayoutId(): Int
    abstract fun initViews(bundle: Bundle?)
    abstract fun initEvent()

    protected fun openActivity(clazz: Class<Any>){
        startActivity(Intent(this, clazz))
    }
    @SuppressLint("CheckResult")
    protected open fun requestPermission(){
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe { granted ->
                    if (granted) {

                    } else {
                        showDialog()
                    }
                }
    }
    protected fun showDialog() {
    }

    override fun finish() {
        super.finish()
        KeyboardUtils.hideSoftInput(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun showToast(toast: String) {
        UniversalToast.makeText(mActivity, toast, UniversalToast.LENGTH_SHORT).showWarning()
    }

    override fun showEmpty() {
        UniversalToast.makeText(mActivity, "暂无数据", UniversalToast.LENGTH_SHORT).showWarning()
    }

    override fun showError(throwable: Throwable) {
        throwable.message?.let { UniversalToast.makeText(mActivity, it, UniversalToast.LENGTH_SHORT).showError() }
    }

    @SuppressLint("CheckResult")
    protected fun doubleClick(view: View, consumer: Consumer<in Any?>) {
        object : ViewClickObservable(view){}
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer)
    }
}