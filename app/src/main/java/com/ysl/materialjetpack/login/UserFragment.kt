//package com.ysl.materialjetpack.login
//
//import android.annotation.SuppressLint
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import com.ysl.materialjetpack.databinding.FraUserBinding
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class UserFragment : Fragment(){
//
//    private val viewModel : UserViewModel by viewModels()
//    private var _binding: FraUserBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
////        return inflater.inflate(R.layout.fra_user, container, false)
//        _binding = FraUserBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    @SuppressLint("SetTextI18n")
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
////        val tv = view.findViewById<TextView>(R.id.tv)
//        viewModel.user.observe(viewLifecycleOwner){ user ->
//            //更新ui
//            binding.tv.text = "${user.id}_${user.name}"
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}