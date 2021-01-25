package com.ysl.materialjetpack.shizhan

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ysl.materialjetpack.databinding.ActWanBinding

class WanActivity : AppCompatActivity() {
    private val tag = "WanActivity"
    private lateinit var binding: ActWanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActWanBinding.inflate(layoutInflater)
        binding.btnRequest.setOnClickListener {
            BaseApi.get<WanApi>(null, WanApi::class.java)
                    .bannerList()
                    .observe(this@WanActivity,
                            { t -> Log.d(tag, "onChanged: $t") })
        }
    }
}