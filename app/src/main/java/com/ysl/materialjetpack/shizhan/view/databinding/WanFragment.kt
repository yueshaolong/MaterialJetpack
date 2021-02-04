package com.ysl.materialjetpack.shizhan.view.databinding

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.ysl.materialjetpack.R
import com.ysl.materialjetpack.databinding.FraWanBinding
import com.ysl.materialjetpack.shizhan.viewmodel.WanViewModel

class WanFragment : BaseFragment<WanViewModel>() {
    companion object{
        const val TAG = "WanFragment"
    }
//    private val wanViewModel : WanViewModel by viewModels()//fragment自己使用的
    private val wanViewModel : WanViewModel by activityViewModels()//使用activity用的viewmodel

    override fun getLayoutId() : Int{
        return R.layout.fra_wan
    }

    override fun initViewModel() {
        vb = wanViewModel
        (binding as FraWanBinding).wanViewModel = wanViewModel
    }

    override fun initViews(bundle: Bundle?) {

    }

    override fun initEvent() {

    }

    override fun initData() {

    }

}