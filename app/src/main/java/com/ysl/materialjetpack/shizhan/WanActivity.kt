package com.ysl.materialjetpack.shizhan

import android.os.Bundle
import androidx.activity.viewModels
import com.ysl.materialjetpack.R
import com.ysl.materialjetpack.databinding.ActWanBinding

class WanActivity : BaseActivity<List<BannerVO>>() {
    private val tag = "WanActivity"
    private lateinit var binding: ActWanBinding
    private val viewModel : WanViewModel by viewModels()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActWanBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        requestPermission()
//        doubleClick(binding.btnRequest, consumer = {
//            viewModel.loadData()
////            viewModel.getFile("a.apk", "1", "cache")
//        })
//
////        binding.btnRequest.setOnClickListener {
////            viewModel.loadData()
////            viewModel.getFile("a.apk", "1", "cache")
//////            BaseApi.get(null, WanApi::class.java)
//////                    .bannerList()
//////                    .observe(this@WanActivity,
//////                            { t -> Log.d(tag, "onChanged: $t") })
////        }
//        viewModel.banner.observe(this){
//            binding.tvUrl.text = it[0].imagePath
//        }
//        viewModel.banner.observe(this){
//            binding.tvContent.text = it[0].title
//        }
//
//        viewModel.progress.observe(this){
//            binding.tvProgress.text = "$it%"
//        }
//        viewModel.downloadFile.observe(this){
//            binding.tvPath.text = it.absolutePath
//        }
//        viewModel.error.observe(this){
//            binding.tvPath.text = it
//        }
//    }

//    override fun requestPermission(){
//        val rxPermissions = RxPermissions(this)
//        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//                .subscribe { granted ->
//                    if (granted) {
//
//                    } else {
//                        showDialog()
//                    }
//                }
//    }

    override fun getLayoutId(): Int {
        return R.layout.act_wan
    }

    override fun initVariables() {
        binding = ActWanBinding.inflate(layoutInflater)
    }

    override fun initViews(bundle: Bundle?) {

    }

    override fun initEvent() {
        binding.btnRequest.setOnClickListener {
            viewModel.loadData()
//            viewModel.getFile("a.apk", "1", "cache")
        }
//        doubleClick(binding.btnRequest, consumer = {
//            viewModel.loadData()
////            viewModel.getFile("a.apk", "1", "cache")
//        })

        viewModel.banner.observe(this){
            binding.tvUrl.text = it[0].imagePath
        }
        viewModel.banner.observe(this){
            binding.tvContent.text = it[0].title
        }

        viewModel.progress.observe(this){
            binding.tvProgress.text = "$it%"
        }
        viewModel.downloadFile.observe(this){
            binding.tvPath.text = it.absolutePath
        }
        viewModel.error.observe(this){
            binding.tvPath.text = it
        }
    }
}