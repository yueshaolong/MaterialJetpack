package com.ysl.materialjetpack.shizhan.view.databinding

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ysl.materialjetpack.R
import com.ysl.materialjetpack.databinding.FraWanBinding
import com.ysl.materialjetpack.shizhan.viewmodel.BannerViewModel
import com.ysl.materialjetpack.shizhan.viewmodel.FileViewModel

class WanFragment : BaseFragment<FileViewModel>() {
    companion object{
        const val TAG = "WanFragment"
    }
    private val wanViewModel : BannerViewModel by activityViewModels()//使用activity用的viewmodel
    private val fileViewModel : FileViewModel by viewModels()//fragment自己使用的

    override fun getLayoutId() : Int{
        return R.layout.fra_wan
    }

    override fun initViewModel() {
        vb = fileViewModel
        (binding as FraWanBinding).wanViewModel = wanViewModel
        (binding as FraWanBinding).fileViewModel = fileViewModel
    }

    override fun initViews(bundle: Bundle?) {

    }

    override fun initEvent() {

    }

    override fun initData() {
        fileViewModel.downloadFile(false,"a.apk", "1", "cache")
    }

}