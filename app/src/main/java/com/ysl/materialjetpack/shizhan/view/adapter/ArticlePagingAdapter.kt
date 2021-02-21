package com.ysl.materialjetpack.shizhan.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ysl.materialjetpack.R
import com.ysl.materialjetpack.databinding.PagingArticleItemBinding
import com.ysl.materialjetpack.shizhan.model.Article
import com.ysl.materialjetpack.shizhan.util.GlideApp

class ArticlePagingAdapter : PagingDataAdapter<Article, ArticleViewHolder>(diff) {
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = getItem(position)
        holder.tvUrl.text = item?.link
        holder.tvContent.text = item?.title
        GlideApp.with(holder.itemView).load(item?.envelopePic).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.error).centerCrop().into(holder.iv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val mBinding = PagingArticleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(mBinding)
    }

    companion object{
        val diff = object : DiffUtil.ItemCallback<Article>(){
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean
            = oldItem == newItem
        }
    }
}

class ArticleViewHolder(mBinding : PagingArticleItemBinding) : RecyclerView.ViewHolder(mBinding.root){
    val iv = mBinding.iv
    val tvUrl = mBinding.tvUrl
    val tvContent = mBinding.tvContent

    init {
        mBinding.root.setOnClickListener {
//            post?.url?.let { url ->
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                view.context.startActivity(intent)
//            }
        }
    }
}