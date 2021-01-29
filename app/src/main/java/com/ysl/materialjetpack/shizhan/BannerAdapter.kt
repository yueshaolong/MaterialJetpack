package com.ysl.materialjetpack.shizhan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ysl.materialjetpack.databinding.BannerItemBinding

open class BannerAdapter(private val dataList: List<BannerVO>)
    : RecyclerView.Adapter<BannerAdapter.ViewHolder>(){

    inner class ViewHolder(mBinding: BannerItemBinding) : RecyclerView.ViewHolder(mBinding.root) {
        val tvUrl = mBinding.tvUrl
        val tvContent = mBinding.tvContent
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerAdapter.ViewHolder {
        val mBinding = BannerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: BannerAdapter.ViewHolder, position: Int) {
        val bannerVO = dataList[position]
        holder.tvUrl.text = bannerVO.url
        holder.tvContent.text = bannerVO.desc
    }

    override fun getItemCount(): Int = dataList.size

}