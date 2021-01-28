package com.ysl.materialjetpack.shizhan

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.ysl.materialjetpack.R
import com.ysl.materialjetpack.databinding.ActWanBinding

class WanActivity : BaseActivity<ActWanBinding, WanViewModel>() {
    private val tag = "WanActivity"
    private val viewModel : WanViewModel by viewModels()
    private val fileViewModel : FileViewModel by viewModels()

    override fun getLayoutId(): Int {
        return R.layout.act_wan
    }

    override fun initVariables() {
        vb = viewModel
    }

    override fun initViews(bundle: Bundle?) {

    }

    override fun initEvent() {
        baseBinding.layoutAppBar.ivRight2.setOnClickListener{
            showToast("有图")
        }
        binding.btnRequest.setOnClickListener {
            Log.d(tag, "initEvent: 点击")
            binding.tvUrl.text = ""
            binding.tvContent.text = ""
            viewModel.loadData()
            fileViewModel.getFile("a.apk", "1", "cache")
        }
//        doubleClick(binding.btnRequest, consumer = {
//            viewModel.loadData()
////            viewModel.getFile("a.apk", "1", "cache")
//        })

        viewModel.banner.observe(this){
            binding.tvUrl.text = it[0].imagePath
        }
        viewModel.banner.observe(this){
            binding.tvContent.text = it[0].title
        }

        fileViewModel.progress.observe(this){
            binding.tvProgress.text = "$it%"
        }
        fileViewModel.downloadFile.observe(this){
            binding.tvPath.text = it.absolutePath
        }
    }
}