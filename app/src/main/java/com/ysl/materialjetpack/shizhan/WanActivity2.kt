package com.ysl.materialjetpack.shizhan

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.ysl.materialjetpack.R
import com.ysl.materialjetpack.databinding.ActWan2Binding
import com.ysl.materialjetpack.databinding.BannerItem2Binding


class WanActivity2 : BaseActivity2<ActWan2Binding, WanViewModel>() {
    companion object{
        const val TAG = "WanActivity"
    }
    private val viewModel : WanViewModel by viewModels()
    private val fileViewModel : FileViewModel by viewModels()
//    lateinit var binding: ActWan2Binding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = DataBindingUtil.setContentView(this, R.layout.act_wan2)
//
//        binding.fileViewModel = fileViewModel
//        binding.wanViewModel = viewModel
//
//        initViews(savedInstanceState)
//        initEvent()
//    }
    override fun getLayoutId(): Int {
        return R.layout.act_wan2
    }
    override fun initVariables() {
        vb = viewModel
        binding.fileViewModel = fileViewModel
        binding.wanViewModel = viewModel
    }
    override fun initViews(bundle: Bundle?) {
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.addItemDecoration(object : RecyclerViewSpacesItemDecoration(0, 10, 0, 10) {})

        binding.rv.adapter = object : BaseQuickAdapter<BannerVO, BaseDataBindingHolder<BannerItem2Binding>>
        (R.layout.banner_item2){
            override fun convert(holder: BaseDataBindingHolder<BannerItem2Binding>, item: BannerVO) {
                val binding: BannerItem2Binding? = holder.dataBinding
                if (binding != null) {
                    binding.bannerVO = item
                    binding.executePendingBindings()
                }
            }
        }
        (binding.rv.adapter as BaseQuickAdapter<*, *>).setOnItemClickListener {
            adapter, view, position ->
                Log.d(TAG, "onItemClick: 点击${viewModel.banner.value?.get(position)}")
        }
}

    override fun initEvent() {
        binding.srl.setOnRefreshListener {
            viewModel.loadData()
        }
        binding.srl.setOnLoadMoreListener {
            viewModel.loadData()
        }

        viewModel.banner.observe(this){
            Log.d("TAG", "initEvent: ${it.size}")

            binding.srl.finishRefresh(true)
            binding.srl.finishLoadMore()

            binding.tvUrl.text = it[0].imagePath
            binding.tvContent.text = it[0].title

            (binding.rv.adapter as BaseQuickAdapter<BannerVO,*>).setList(it)
        }

        fileViewModel.progress.observe(this){
            binding.tvProgress.text = "$it%"
        }
        fileViewModel.downloadFile.observe(this){
            binding.tvPath.text = it.absolutePath
        }

    }
}