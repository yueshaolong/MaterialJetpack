package com.ysl.materialjetpack.shizhan.view.databinding

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.KeyboardUtils
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.tbruyelle.rxpermissions2.RxPermissions
import com.ysl.loadsirlibrary.EmptyCallback
import com.ysl.loadsirlibrary.ErrorCallback
import com.ysl.loadsirlibrary.LoadingCallback
import com.ysl.materialjetpack.R
import com.ysl.materialjetpack.databinding.ActBase2Binding
import com.ysl.materialjetpack.shizhan.view.weiget.ViewClickObservable
import com.ysl.materialjetpack.shizhan.viewmodel.BaseViewModel
import com.ysl.materialjetpack.shizhan.viewmodel.ToolBarViewModel
import io.reactivex.functions.Consumer
import xyz.bboylin.universialtoast.UniversalToast
import java.util.concurrent.TimeUnit

abstract class BaseActivity2<T : ViewDataBinding, VB : BaseViewModel> : AppCompatActivity() {
    lateinit var baseBinding: ActBase2Binding
    lateinit var binding: T
    lateinit var vb: VB
    lateinit var mActivity : Activity
    lateinit var imm : InputMethodManager
    lateinit var layoutView: View
    lateinit var loadService : LoadService<Any>

    val toolBarViewModel : ToolBarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE) //设置无ActionBar
        mActivity = this
        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        baseBinding = DataBindingUtil.inflate(layoutInflater, R.layout.act_base2, null, false)
        baseBinding.layoutAppBar.toolBarViewModel = toolBarViewModel
        baseBinding.lifecycleOwner = this

        binding = DataBindingUtil.inflate(layoutInflater, getLayoutId(), baseBinding.flContent, true)
        binding.lifecycleOwner = this
        setContentView(baseBinding.root)
        layoutView = binding.root

        initViewModel()
        setBaseListener()

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

    fun click(view: View){
        when(view.id){
            R.id.iv_back -> finish()
            R.id.iv_right -> showToast("点击了问号")
            R.id.iv_right2 -> showToast("点击了搜索")
        }
    }

    private fun setBaseListener() {
        loadService = LoadSir.getDefault().register(layoutView)
        loadService.showSuccess()

        vb.error.observe(this){
            if (it.isActivity) {
                showError(it.throwable)
                setErrorCallBack()
            }
        }
        vb.empty.observe(this){
            if (it.isActivity) {
                showToast("暂无数据")
                setEmptyCallBack(true, "")
            }
        }
        vb.loading.observe(this){
            if (it.isActivity) {
                if (it.isLoading) {
                    loadService.showCallback(LoadingCallback::class.java)
                } else {
                    loadService.showSuccess()
                }
            }
        }
    }

    fun setErrorCallBack() {
        ErrorCallback.listener = View.OnClickListener {
            loadService.showCallback(LoadingCallback::class.java)
            initData()
        }
        loadService.showCallback(ErrorCallback::class.java)
    }

    fun setEmptyCallBack(showBtn: Boolean, text: String) {
        EmptyCallback.showBtn = showBtn
        EmptyCallback.text = if (TextUtils.isEmpty(text)) "暂无数据" else text
        EmptyCallback.listener = View.OnClickListener {
            finish()
        }
        loadService.showCallback(EmptyCallback::class.java)
    }

    protected fun showTitle(){
        toolBarViewModel.centerShow.value = true
        toolBarViewModel.ivBack.value = false
        toolBarViewModel.ivRight.value = false
        toolBarViewModel.ivRight2.value = false
    }
    protected fun showBackTitle(){
        toolBarViewModel.centerShow.value = true
        toolBarViewModel.ivBack.value = true
        toolBarViewModel.ivRight.value = false
        toolBarViewModel.ivRight2.value = false
    }

    protected fun toActivity(clazz: Class<out Activity>){//子类泛型对象可以赋值给父类泛型对象，用 out。
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

    fun showToast(toast: String) {
        UniversalToast.makeText(mActivity, toast, UniversalToast.LENGTH_SHORT).showWarning()
    }

    fun showError(throwable: Throwable) {
        throwable.message?.let { UniversalToast.makeText(mActivity, it, UniversalToast.LENGTH_SHORT).showError() }
    }

    @SuppressLint("CheckResult")
    protected fun doubleClick(view: View, consumer: Consumer<in Any?>) {
        object : ViewClickObservable(view){}
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(consumer)//in 代替了? super
    }

    override fun onDestroy() {
        super.onDestroy()
        vb.mDisposable.dispose()
    }
}