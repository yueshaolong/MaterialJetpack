package com.ysl.materialjetpack.shizhan.view.databinding

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.ysl.materialjetpack.R
import com.ysl.materialjetpack.databinding.ActListBinding
import com.ysl.materialjetpack.databinding.ArticleItemBinding
import com.ysl.materialjetpack.shizhan.model.Article
import com.ysl.materialjetpack.shizhan.view.weiget.RecyclerViewSpacesItemDecoration
import com.ysl.materialjetpack.shizhan.viewmodel.ArticleViewModel

class ListActivity : BaseActivity2<ActListBinding, ArticleViewModel>() {
    companion object{
        const val TAG = "ListActivity"
    }

    private val articleViewModel : ArticleViewModel by viewModels()
    private var refresh: Boolean = true
    private val adapter =
        object : BaseQuickAdapter<Article, BaseDataBindingHolder<ArticleItemBinding>>(R.layout.article_item){
            override fun convert(holder: BaseDataBindingHolder<ArticleItemBinding>, item: Article) {
                val binding: ArticleItemBinding? = holder.dataBinding
                if (binding != null) {
                    binding.article = item
                    binding.executePendingBindings()
                }
            }
        }

    override fun getLayoutId(): Int {
        return R.layout.act_list
    }

    override fun initViewModel() {
        vb = articleViewModel
        binding.articleViewModel = articleViewModel
    }

    override fun initViews(bundle: Bundle?) {
        showBackTitle()
        toolBarViewModel.centerText.value = "列表"

        binding.rv.addItemDecoration(object : RecyclerViewSpacesItemDecoration(0, 10, 0, 10) {})
        binding.rv.adapter = adapter

        //为整个条目设置点击事件
        adapter.setOnItemClickListener { adapter, view, position ->
            val article : Article = adapter.getItem(position) as Article
            Log.d(localClassName, "onItemClick: 点击${article}")
            showToast("$position ---> ${article.title}")
        }
        //为item内某个view设置点击事件
        adapter.addChildClickViewIds(R.id.iv, R.id.tv_url, R.id.tv_content)
        adapter.setOnItemChildClickListener { adapter, view, position ->
            val article : Article = adapter.getItem(position) as Article
            when(view.id){
                R.id.iv -> showToast("图片地址：${article.envelopePic}")
                R.id.tv_url -> showToast(article.link)
                R.id.tv_content -> showToast(article.title)
            }
        }
    }

    override fun initEvent() {
        binding.srl.setOnRefreshListener {
            refresh = true
            articleViewModel.loadArticle(true, 0, false)
        }
        binding.srl.setOnLoadMoreListener {
            refresh = false
            articleViewModel.article.value?.let { it1 -> articleViewModel.loadArticle(true, it1.curPage, false) }
        }

        articleViewModel.article.observe(this){
            if (refresh) {
                binding.srl.finishRefresh(true)
                adapter.setList(it.datas)
            } else {
                binding.srl.finishLoadMore()
                adapter.addData(it.datas)
            }
        }
    }

    override fun initData() {
        articleViewModel.loadArticle(true, 0, true)
    }
}