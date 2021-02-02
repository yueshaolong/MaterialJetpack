package com.ysl.materialjetpack.shizhan

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
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
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.addItemDecoration(object : RecyclerViewSpacesItemDecoration(0, 5, 0, 5) {})
    }

    override fun initEvent() {
        baseBinding.layoutAppBar.ivRight2.setOnClickListener{
            showToast("有图")
        }
        doubleClick(binding.btnRequest, consumer = {
            viewModel.loadData()
//            fileViewModel.getFile("a.apk", "1", "cache")
        })
        binding.srl.setOnRefreshListener {
            viewModel.loadData()
        }
        binding.srl.setOnLoadMoreListener {

        }

        viewModel.banner.observe(this){
            Log.d("TAG", "initEvent: ${it.size}")
            binding.srl.finishRefresh(true)
            binding.tvUrl.text = it[0].imagePath
            binding.tvContent.text = it[0].title

            binding.rv.adapter = object : BannerAdapter(this, it){}
//            binding.rv.adapter =
//                    object : BaseQuickAdapter<BannerVO, BaseDataBindingHolder<BannerItem2Binding>>
//                    (R.layout.banner_item2, it as MutableList<BannerVO>?){
//                        override fun convert(holder: BaseDataBindingHolder<BannerItem2Binding>, item: BannerVO) {
//                            val binding: BannerItem2Binding? = holder.dataBinding
//                            if (binding != null) {
//                                binding.bannerVO = item
//                            }
//                        }
//                    }
        }

        fileViewModel.progress.observe(this){
            binding.tvProgress.text = "$it%"
        }
        fileViewModel.downloadFile.observe(this){
            binding.tvPath.text = it.absolutePath
        }

    }
}