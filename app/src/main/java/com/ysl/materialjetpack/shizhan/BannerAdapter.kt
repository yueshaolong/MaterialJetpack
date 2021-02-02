package com.ysl.materialjetpack.shizhan

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.ysl.materialjetpack.R
import com.ysl.materialjetpack.databinding.BannerItemBinding

open class BannerAdapter(val context: Context, private val dataList: List<BannerVO>)
    : RecyclerView.Adapter<BannerAdapter.ViewHolder>(){

    inner class ViewHolder(mBinding: BannerItemBinding) : RecyclerView.ViewHolder(mBinding.root) {
        val tvUrl = mBinding.tvUrl
        val tvContent = mBinding.tvContent
        val iv = mBinding.iv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerAdapter.ViewHolder {
        val mBinding = BannerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: BannerAdapter.ViewHolder, position: Int) {
        val bannerVO = dataList[position]
        holder.tvUrl.text = bannerVO.url
        holder.tvContent.text = bannerVO.title
        Glide.with(context)
                .load(bannerVO.imagePath)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .into(holder.iv)
        holder.itemView.setOnClickListener {
            ToastUtils.showShort("点击了：$position")
        }
    }

    override fun getItemCount(): Int = dataList.size

}