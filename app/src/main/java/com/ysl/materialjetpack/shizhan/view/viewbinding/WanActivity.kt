package com.ysl.materialjetpack.shizhan.view.viewbinding

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ysl.materialjetpack.R
import com.ysl.materialjetpack.databinding.ActWanBinding
import com.ysl.materialjetpack.shizhan.view.adapter.BannerAdapter
import com.ysl.materialjetpack.shizhan.view.weiget.RecyclerViewSpacesItemDecoration
import com.ysl.materialjetpack.shizhan.viewmodel.BannerViewModel
import com.ysl.materialjetpack.shizhan.viewmodel.FileViewModel


class WanActivity : BaseActivity<ActWanBinding, BannerViewModel>() {
    private val tag = "WanActivity"
    private val viewModel : BannerViewModel by viewModels()
    private val fileViewModel : FileViewModel by viewModels()

    override fun getLayoutId(): Int {
        return R.layout.act_wan
    }

    override fun initViewModel() {
        vb = viewModel
    }

    override fun initViews(bundle: Bundle?) {
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.addItemDecoration(object : RecyclerViewSpacesItemDecoration(0, 5, 0, 5) {})
    }

    override fun initEvent() {
        baseBinding.layoutAppBar.ivRight2.setOnClickListener{
            showToast("有图")
        }
        doubleClick(binding.btnRequest, consumer = {
//            viewModel.loadData()
//            fileViewModel.getFile("a.apk", "1", "cache")
        })
        binding.srl.setOnRefreshListener {
//            viewModel.loadData()
        }
        binding.srl.setOnLoadMoreListener {

        }

        viewModel.banner.observe(this){
            Log.d("TAG", "initEvent: ${it.size}")
            binding.srl.finishRefresh(true)
            binding.tvUrl.text = it[0].imagePath
            binding.tvContent.text = it[0].title

            binding.rv.adapter = BannerAdapter(this, it)
        }

        fileViewModel.progress.observe(this){
            binding.tvProgress.text = "$it%"
        }
        fileViewModel.downloadFile.observe(this){
            binding.tvPath.text = it.absolutePath
        }

    }
}