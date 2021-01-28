package com.ysl.materialjetpack.shizhan

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.KeyboardUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import com.ysl.materialjetpack.databinding.ActBaseBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import xyz.bboylin.universialtoast.UniversalToast
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

abstract class BaseActivity<T: ViewBinding, VB: BaseViewModel> : AppCompatActivity(),UICallBack {
    lateinit var baseBinding: ActBaseBinding
    lateinit var binding: T
    lateinit var vb: VB
    protected lateinit var mActivity : Activity
    protected lateinit var imm : InputMethodManager
    protected lateinit var layoutView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE) //设置无ActionBar
        mActivity = this
        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        initVariables()
        baseBinding = ActBaseBinding.inflate(layoutInflater)
        layoutView = View.inflate(this, getLayoutId(), null)
        baseBinding.flContent.addView(layoutView)
        setContentView(baseBinding.root)
        bindView()
        initViews(savedInstanceState)
        initEvent()
        requestPermission()
    }
    abstract fun initVariables()
    abstract fun getLayoutId(): Int
    abstract fun initViews(bundle: Bundle?)
    abstract fun initEvent()

    override fun onStart() {
        super.onStart()
        baseBinding.layoutAppBar.ivBack.setOnClickListener{
            finish()
        }
        baseBinding.layoutAppBar.ivRight2.setOnClickListener {
            vb.message.postValue("dffd")
        }
        vb.message.observe(this){
            showToast(it)
        }
        vb.empty.observe(this){
            showToast("kong")
        }
    }

    private fun bindView(){
        val superclass: Type = javaClass.genericSuperclass
        val aClass = (superclass as ParameterizedType).actualTypeArguments[0] as Class<*>
        try {
            val method: Method = aClass.getDeclaredMethod("bind", View::class.java)
            binding = method.invoke(null, layoutView) as T
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
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
        KeyboardUtils.hideSoftInput(this)
        super.finish()
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