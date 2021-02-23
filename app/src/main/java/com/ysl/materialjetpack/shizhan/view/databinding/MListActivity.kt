package com.ysl.materialjetpack.shizhan.view.databinding

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.ysl.materialjetpack.R
import com.ysl.materialjetpack.databinding.ArticleItemBinding
import com.ysl.materialjetpack.databinding.MactListBinding
import com.ysl.materialjetpack.shizhan.model.Article
import com.ysl.materialjetpack.shizhan.view.weiget.RecyclerViewSpacesItemDecoration
import com.ysl.materialjetpack.shizhan.viewmodel.ArticleViewModel
import xyz.bboylin.universialtoast.UniversalToast

class MListActivity : AppCompatActivity() {
    companion object{
        const val TAG = "MListActivity"
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
    lateinit var binding: MactListBinding
    lateinit var layoutView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE) //设置无ActionBar
        if (Build.VERSION.SDK_INT >= 21) {//透明状态栏效果
            val decorView = window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT
        }

        binding = DataBindingUtil.inflate(layoutInflater, getLayoutId(), null, true)
        binding.lifecycleOwner = this
        setContentView(binding.root)
        layoutView = binding.root

        initViewModel()

        initViews(savedInstanceState)
        initEvent()
        initData()
    }

     fun getLayoutId(): Int {
        return R.layout.mact_list
    }

     fun initViewModel() {
        binding.articleViewModel = articleViewModel
    }

     fun initViews(bundle: Bundle?) {

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
    fun showToast(toast: String) {
        UniversalToast.makeText(this, toast, UniversalToast.LENGTH_SHORT).showWarning()
    }
     fun initEvent() {
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

     fun initData() {
        articleViewModel.loadArticle(true, 0, true)
    }
}