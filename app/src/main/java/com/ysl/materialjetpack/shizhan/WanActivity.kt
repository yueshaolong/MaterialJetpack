package com.ysl.materialjetpack.shizhan

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ysl.materialjetpack.databinding.ActWanBinding

class WanActivity : AppCompatActivity() {
    private val tag = "WanActivity"
    private lateinit var binding: ActWanBinding
    private val viewModel : WanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActWanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRequest.setOnClickListener {
            viewModel.loadData()
//            BaseApi.get(null, WanApi::class.java)
//                    .bannerList()
//                    .observe(this@WanActivity,
//                            { t -> Log.d(tag, "onChanged: $t") })
        }
//        viewModel.banner.observe(this){
//            binding.tvUrl.text = it[0].imagePath
//        }
//        viewModel.banner.observe(this){
//            binding.tvContent.text = it[0].title
//        }
        viewModel.progressLiveData.observe(this){
            binding.tvUrl.text = "$it%"
        }
        viewModel.body.observe(this){

        }
    }
}