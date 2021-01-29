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
//                    object : BaseQuickAdapter<BannerVO, BaseViewHolder>(R.layout.banner_item, it as MutableList<BannerVO>?){
//                override fun onItemViewHolderCreated(@NotNull viewHolder: BaseViewHolder, viewType: Int) {
//                    // 绑定 view
//                    DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
////                    BannerItemBinding.bind(viewHolder.itemView)
//                }
//                override fun convert(holder: BaseViewHolder, item: BannerVO) {
//                    val binding: ViewDataBinding? = holder.getBinding()
//                    binding.set item.url
//                    binding.tvContent.text = item.desc
//
//                    doubleClick(holder.itemView){
//                        showToast("点击条目${holder.adapterPosition}")
//                    }
//                }
//            }
        }

        fileViewModel.progress.observe(this){
            binding.tvProgress.text = "$it%"
        }
        fileViewModel.downloadFile.observe(this){
            binding.tvPath.text = it.absolutePath
        }

    }
}