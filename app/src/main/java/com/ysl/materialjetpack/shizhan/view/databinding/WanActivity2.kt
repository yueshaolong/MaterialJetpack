package com.ysl.materialjetpack.shizhan.view.databinding

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.ysl.materialjetpack.R
import com.ysl.materialjetpack.databinding.ActWan2Binding
import com.ysl.materialjetpack.databinding.ListItemBinding
import com.ysl.materialjetpack.shizhan.hilt.HiltActivity
import com.ysl.materialjetpack.shizhan.view.weiget.RecyclerViewSpacesItemDecoration
import com.ysl.materialjetpack.shizhan.viewmodel.BannerViewModel

class WanActivity2 : BaseActivity2<ActWan2Binding, BannerViewModel>() {
    companion object{
        const val TAG = "WanActivity"
    }

    private val wanViewModel : BannerViewModel by viewModels()

    private val adapter =
            object : BaseQuickAdapter<String, BaseDataBindingHolder<ListItemBinding>>(R.layout.list_item){
                override fun convert(holder: BaseDataBindingHolder<ListItemBinding>, item: String) {
                    val binding: ListItemBinding? = holder.dataBinding
                    if (binding != null) {
                        binding.where = item
                        binding.executePendingBindings()
                    }
                }
            }

    override fun getLayoutId(): Int {
        return R.layout.act_wan2
    }
    override fun initViewModel() {
        vb = wanViewModel
        binding.wanViewModel = wanViewModel
    }
    override fun initViews(bundle: Bundle?) {
        //默认蓝色状态栏，也可以单独设置状态栏颜色
        baseBinding.layoutAppBar.appbar.setBackgroundColor(Color.GREEN)

        showTitle()
        toolBarViewModel.centerText.value = "首页"

        go2Fragment()

        binding.rv.addItemDecoration(object : RecyclerViewSpacesItemDecoration(0, 10, 0, 10){})
        binding.rv.adapter = adapter
        //为整个条目设置点击事件
        adapter.setOnItemClickListener { adapter, view, position ->
            val where : String = adapter.getItem(position) as String
            Log.d(localClassName, "onItemClick: 点击${where}")
            when(where){
                getString(R.string.request) -> wanViewModel.loadBanner(true)
                getString(R.string.list1) -> toActivity(MListActivity::class.java)
                getString(R.string.list2) -> toActivity(MListActivity2::class.java)
                getString(R.string.paging) -> toActivity(PagingListActivity::class.java)
                getString(R.string.toolbar) -> toActivity(ToolbarActivity::class.java)
                getString(R.string.hilt) -> toActivity(HiltActivity::class.java)
            }
        }
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
//        doubleClick(binding.btnList){
//            toActivity(ListActivity::class.java)
//        }
//        binding.btnList.setOnClickListener {
//            toActivity(ListActivity::class.java)
//        }
//        binding.pagingList.setOnClickListener {
//            toActivity(PagingListActivity::class.java)
//        }

        wanViewModel.where.observe(this){
            adapter.setList(it)
        }
    }
//    fun clickListener(view: View){
//        when(view.id){
//            R.id.btn_list -> {
//                toActivity(MListActivity::class.java)
//                showToast("lalalal")
//            }
//            R.id.btn_list2 -> {
//                toActivity(MListActivity2::class.java)
//            }
//            R.id.paging_list -> toActivity(PagingListActivity::class.java)
//            R.id.toolbar -> toActivity(ToolbarActivity::class.java)
//            R.id.hilt -> toActivity(HiltActivity::class.java)
//        }
//    }
    override fun initData() {
        wanViewModel.loadBanner(true)
    }
}