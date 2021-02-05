package com.ysl.materialjetpack.shizhan.view.databinding

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.ysl.loadsirlibrary.EmptyCallback
import com.ysl.loadsirlibrary.ErrorCallback
import com.ysl.loadsirlibrary.LoadingCallback
import com.ysl.materialjetpack.shizhan.viewmodel.BaseViewModel
import xyz.bboylin.universialtoast.UniversalToast

abstract class BaseFragment<VB: BaseViewModel> : Fragment() {
    private var _binding: ViewDataBinding? = null
    val binding get() = _binding!!
    lateinit var vb: VB
    lateinit var layoutView: View
    lateinit var loadService : LoadService<Any>
    lateinit var mActivity : FragmentActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(layoutInflater, getLayoutId(), null, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = requireActivity()
        layoutView = binding.root

        initViewModel()
        setBaseListener()

        initViews(savedInstanceState)
        initEvent()
        initData()
    }

    abstract fun getLayoutId() : Int
    abstract fun initViewModel()
    abstract fun initViews(bundle: Bundle?)
    abstract fun initEvent()
    abstract fun initData()

    private fun setBaseListener() {
        loadService = LoadSir.getDefault().register(layoutView)
        loadService.showSuccess()

        vb.error.observe(viewLifecycleOwner){
            if (!it.isActivity) {
                showError(it.throwable)
                setErrorCallBack()
            }
        }
        vb.empty.observe(viewLifecycleOwner){
            if (!it.isActivity) {
                showToast("暂无数据")
                setEmptyCallBack(true, "")
            }
        }
        vb.loading.observe(viewLifecycleOwner){
            if (!it.isActivity) {
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
            mActivity.finish()
        }
        loadService.showCallback(EmptyCallback::class.java)
    }
    fun showToast(toast: String) {
        UniversalToast.makeText(mActivity, toast, UniversalToast.LENGTH_SHORT).showWarning()
    }
    fun showError(throwable: Throwable) {
        throwable.message?.let { UniversalToast.makeText(mActivity, it, UniversalToast.LENGTH_SHORT).showError() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}