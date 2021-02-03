package com.ysl.materialjetpack.shizhan.view.databinding

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.KeyboardUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import com.ysl.materialjetpack.R
import com.ysl.materialjetpack.databinding.ActBase2Binding
import com.ysl.materialjetpack.shizhan.view.UICallBack
import com.ysl.materialjetpack.shizhan.view.weiget.ViewClickObservable
import com.ysl.materialjetpack.shizhan.viewmodel.BaseViewModel
import io.reactivex.functions.Consumer
import xyz.bboylin.universialtoast.UniversalToast
import java.util.concurrent.TimeUnit

abstract class BaseActivity2<T: ViewDataBinding, VB: BaseViewModel> : AppCompatActivity(), UICallBack {
    lateinit var baseBinding: ActBase2Binding
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
        baseBinding = DataBindingUtil.inflate(layoutInflater, R.layout.act_base2, null, false)
        binding = DataBindingUtil.inflate(layoutInflater, getLayoutId(), baseBinding.flContent, true)
        setContentView(baseBinding.root)
        initViewModel()
        initViews(savedInstanceState)
        initEvent()
        requestPermission()
        initData()
    }

    abstract fun getLayoutId(): Int
    abstract fun initViewModel()
    abstract fun initViews(bundle: Bundle?)
    abstract fun initEvent()
    abstract fun initData()

    override fun onStart() {
        super.onStart()
        baseBinding.layoutAppBar.ivBack.setOnClickListener{
            finish()
        }
        baseBinding.layoutAppBar.ivRight.setOnClickListener {
            vb.message.postValue("点击了问号")
        }
        baseBinding.layoutAppBar.ivRight2.setOnClickListener {
            vb.message.postValue("点击了搜索")
        }
        vb.message.observe(this){
            showToast(it)
        }
        vb.empty.observe(this){
            showToast("暂无数据")
        }
        vb.loading.observe(this){
            showToast("加载中...")
        }
    }

//    private fun bindView(){
//        val superclass: Type = javaClass.genericSuperclass
//        val aClass = (superclass as ParameterizedType).actualTypeArguments[0] as Class<*>
//        try {
//            val method: Method = aClass.getDeclaredMethod("bind", View::class.java)
//            binding = method.invoke(null, layoutView) as T
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
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
                .subscribe(consumer)
    }

    override fun onDestroy() {
        super.onDestroy()
        vb.mDisposable.dispose()
    }
}