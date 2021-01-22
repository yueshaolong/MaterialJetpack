package com.ysl.myapplication.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ysl.myapplication.R
import com.ysl.myapplication.databinding.FraUserBinding

class UserFragment : Fragment(){

    private val viewModel : UserViewModel by viewModels()
    private lateinit var binding: FraUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fra_user, container, false)
        binding = FraUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val tv = view.findViewById<TextView>(R.id.tv)
        viewModel.user.observe(viewLifecycleOwner){ user ->
            //更新ui
            binding.tv.text = "${user.id}_${user.name}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}