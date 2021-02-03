package com.ysl.materialjetpack.shizhan.view.databinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ysl.materialjetpack.R
import com.ysl.materialjetpack.databinding.FraWanBinding
import com.ysl.materialjetpack.shizhan.viewmodel.WanViewModel

class WanFragment : Fragment() {
    companion object{
        const val TAG = "WanFragment"
    }
    private val wanViewModel : WanViewModel by viewModels()
    lateinit var binding : FraWanBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fra_wan, container, false)
//        binding = FraWanBinding.inflate(layoutInflater)
        binding.wanViewModel = wanViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.wanViewModel = wanViewModel
//
//        wanViewModel.banner.observe(viewLifecycleOwner){
//            binding.tvFra.text = it[0].toString()
//        }
    }

//    override fun initBinding() : ViewDataBinding?{
//        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fra_wan, null, false)
//        return _binding
//    }
//
//    override fun initViewModel(_binding: ViewDataBinding?) {
//        vb = wanViewModel
//        (_binding as FraWanBinding).wanViewModel = wanViewModel
//    }
//
//    override fun initViews(bundle: Bundle?) {
//
//    }
//
//    override fun initEvent() {
//        binding.tv
//    }
//
//    override fun initData() {
//
//    }

}