//package com.ysl.materialjetpack.shizhan.view.databinding
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.databinding.ViewDataBinding
//import androidx.fragment.app.Fragment
//import com.ysl.materialjetpack.shizhan.viewmodel.BaseViewModel
//
//abstract class BaseFragment<VB: BaseViewModel> : Fragment() {
//    var _binding: ViewDataBinding? = null
//    var binding get() = _binding!!
//    lateinit var vb: VB
//
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View {
//        _binding = initBinding()
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        initViewModel(_binding)
//        initViews(savedInstanceState)
//        initEvent()
//        initData()
//    }
//
//    abstract fun initBinding() : ViewDataBinding?
//    abstract fun initViewModel(_binding: ViewDataBinding?)
//    abstract fun initViews(bundle: Bundle?)
//    abstract fun initEvent()
//    abstract fun initData()
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}