package com.ysl.materialjetpack.shizhan.view.databinding

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.ysl.materialjetpack.R
import com.ysl.materialjetpack.databinding.ActPagingListBinding
import com.ysl.materialjetpack.shizhan.view.adapter.ArticlePagingAdapter
import com.ysl.materialjetpack.shizhan.view.weiget.RecyclerViewSpacesItemDecoration
import com.ysl.materialjetpack.shizhan.viewmodel.PagingArticleViewModel
import kotlinx.coroutines.flow.collectLatest

class PagingListActivity : BaseActivity2<ActPagingListBinding, PagingArticleViewModel>() {
    companion object{
        const val TAG = "PagingListActivity"
    }

    private val pagingArticleViewModel : PagingArticleViewModel by viewModels()
    private var refresh: Boolean = true
    private val adapter = ArticlePagingAdapter()

    override fun getLayoutId(): Int {
        return R.layout.act_paging_list
    }

    override fun initViewModel() {
        vb = pagingArticleViewModel
        binding.articleViewModel = pagingArticleViewModel
    }

    override fun initViews(bundle: Bundle?) {
        showBackTitle()
        toolBarViewModel.centerText.value = "列表"

        binding.rv.addItemDecoration(object : RecyclerViewSpacesItemDecoration(0, 10, 0, 10) {})
        binding.rv.adapter = adapter

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest {
//                binding.srl.isRefreshing = it.refresh is LoadState.Loading
            }
        }
        lifecycleScope.launchWhenCreated {
//            pagingArticleViewModel.posts.collectLatest {
//                adapter.submitData(it)
//            }
        }
        lifecycleScope.launchWhenCreated {
//            adapter.loadStateFlow
//                    .distinctUntilChangedBy { it.refresh }
//                    .filter { it.refresh is LoadState.NotLoading }
//                    .collect { binding.rv.scrollToPosition(0) }
        }



        //为整个条目设置点击事件
//        adapter.setOnItemClickListener { adapter, view, position ->
//            val article : Article = adapter.getItem(position) as Article
//            Log.d(localClassName, "onItemClick: 点击${article}")
//            showToast("$position ---> ${article.title}")
//        }
//        //为item内某个view设置点击事件
//        adapter.addChildClickViewIds(R.id.iv, R.id.tv_url, R.id.tv_content)
//        adapter.setOnItemChildClickListener { adapter, view, position ->
//            val article : Article = adapter.getItem(position) as Article
//            when(view.id){
//                R.id.iv -> showToast("图片地址：${article.envelopePic}")
//                R.id.tv_url -> showToast(article.link)
//                R.id.tv_content -> showToast(article.title)
//            }
//        }
    }

    override fun initEvent() {
        binding.srl.setOnRefreshListener {
            refresh = true
            pagingArticleViewModel.loadArticle(true, 0, false)
        }
//        binding.srl.setOnLoadMoreListener {
//            refresh = false
//            pagingArticleViewModel.article.value?.let { it1 -> pagingArticleViewModel.loadArticle(true, it1.curPage, false) }
//        }

        pagingArticleViewModel.article.observe(this){
            if (refresh) {
//                binding.srl.finishRefresh(true)
//                adapter.setList(it.datas)
            } else {
//                binding.srl.finishLoadMore()
//                adapter.addData(it.datas)
            }
        }
    }

    override fun initData() {
        pagingArticleViewModel.loadArticle(true, 0, true)
    }
}