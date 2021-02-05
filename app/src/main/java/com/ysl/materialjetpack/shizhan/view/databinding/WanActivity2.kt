package com.ysl.materialjetpack.shizhan.view.databinding

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.ysl.materialjetpack.R
import com.ysl.materialjetpack.databinding.ActWan2Binding
import com.ysl.materialjetpack.shizhan.viewmodel.BannerViewModel


class WanActivity2 : BaseActivity2<ActWan2Binding, BannerViewModel>() {
    companion object{
        const val TAG = "WanActivity"
    }
    private val wanViewModel : BannerViewModel by viewModels()

    override fun getLayoutId(): Int {
        return R.layout.act_wan2
    }
    override fun initViewModel() {
        vb = wanViewModel
        binding.wanViewModel = wanViewModel
    }
    override fun initViews(bundle: Bundle?) {
        showTitle()
        toolBarViewModel.centerText.value = "首页"

        go2Fragment()
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
        binding.btnList.setOnClickListener {
            toActivity(ListActivity::class.java)
        }
    }

    override fun initData() {
        wanViewModel.loadBanner(true)
    }
}