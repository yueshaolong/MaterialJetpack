package com.ysl.materialjetpack.shizhan.view.databinding

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.ysl.materialjetpack.R
import com.ysl.materialjetpack.databinding.ActWan2Binding
import com.ysl.materialjetpack.databinding.BannerItem2Binding
import com.ysl.materialjetpack.shizhan.model.BannerVO
import com.ysl.materialjetpack.shizhan.view.weiget.RecyclerViewSpacesItemDecoration
import com.ysl.materialjetpack.shizhan.viewmodel.FileViewModel
import com.ysl.materialjetpack.shizhan.viewmodel.WanViewModel


class WanActivity2 : BaseActivity2<ActWan2Binding, WanViewModel>() {
    companion object{
        const val TAG = "WanActivity"
    }
    private val wanViewModel : WanViewModel by viewModels()
    private val fileViewModel : FileViewModel by viewModels()

    override fun getLayoutId(): Int {
        return R.layout.act_wan2
    }
    override fun initViewModel() {
        vb = wanViewModel
        binding.wanViewModel = wanViewModel
        binding.fileViewModel = fileViewModel
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
        (binding.rv.adapter as BaseQuickAdapter<*, *>).setOnItemClickListener { adapter, view, position ->
                Log.d(TAG, "onItemClick: 点击${wanViewModel.banner.value?.get(position)}")
        }

//        go2Fragment()
    }

    private fun go2Fragment() {
        var fragment: Fragment? = supportFragmentManager.findFragmentByTag("TAG_WU_HD")
        if (fragment == null) {
            fragment = WanFragment()
        }
        val b = Bundle()
        b.putString("FROM", "CheckInAct")
        fragment.arguments = b
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.id_container, fragment, "TAG_WU_HD")
        transaction.commit()
    }

    override fun initEvent() {
        binding.srl.setOnRefreshListener {
            wanViewModel.loadData()
        }
        binding.srl.setOnLoadMoreListener {
            wanViewModel.loadData()
        }

        wanViewModel.banner.observe(this){
            Log.d("TAG", "initEvent: ${it.size}")

            binding.srl.finishRefresh(true)
            binding.srl.finishLoadMore()

//            binding.tvUrl.text = it[0].imagePath
//            binding.tvContent.text = it[0].title

            (binding.rv.adapter as BaseQuickAdapter<BannerVO, *>).setList(it)
        }

        doubleClick(binding.tvPath){
            fileViewModel.getFile("a.apk", "1", "cache")
        }
        fileViewModel.progress.observe(this){
//            binding.tvProgress.text = "$it%"
        }
        fileViewModel.downloadFile.observe(this){
//            binding.tvPath.text = it.absolutePath
        }
    }

    override fun initData() {
        wanViewModel.loadData()
        fileViewModel.getFile("a.apk", "1", "cache")
    }
}