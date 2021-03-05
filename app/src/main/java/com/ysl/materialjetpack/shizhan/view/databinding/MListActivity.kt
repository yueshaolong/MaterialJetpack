package com.ysl.materialjetpack.shizhan.view.databinding

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import cn.jiguang.share.android.api.PlatActionListener
import cn.jiguang.share.android.api.Platform
import cn.jiguang.share.android.api.ShareParams
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.google.android.material.appbar.AppBarLayout
import com.ysl.materialjetpack.App
import com.ysl.materialjetpack.R
import com.ysl.materialjetpack.databinding.ArticleItemBinding
import com.ysl.materialjetpack.databinding.MactListBinding
import com.ysl.materialjetpack.shizhan.model.Article
import com.ysl.materialjetpack.shizhan.view.weiget.RecyclerViewSpacesItemDecoration
import com.ysl.materialjetpack.shizhan.viewmodel.ArticleViewModel
import com.ysl.materialjetpack.shizhan.viewmodel.ToolBarViewModel
import xyz.bboylin.universialtoast.UniversalToast
import java.util.*

class MListActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MListActivity"
    }

    private val articleViewModel: ArticleViewModel by viewModels()
    private val toolBarViewModel: ToolBarViewModel by viewModels()
    private var refresh: Boolean = true
    private val adapter =
            object : BaseQuickAdapter<Article, BaseDataBindingHolder<ArticleItemBinding>>(R.layout.article_item) {
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
        chenJinShi(true)

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
        binding.toolBarViewModel = toolBarViewModel
        binding.articleViewModel = articleViewModel
    }

    fun initViews(bundle: Bundle?) {

        binding.rv.addItemDecoration(object : RecyclerViewSpacesItemDecoration(0, 10, 0, 10) {})
        binding.rv.adapter = adapter

        //为整个条目设置点击事件
        adapter.setOnItemClickListener { adapter, view, position ->
            val article: Article = adapter.getItem(position) as Article
            Log.d(localClassName, "onItemClick: 点击${article}")
            showToast("$position ---> ${article.title}")
        }
        //为item内某个view设置点击事件
        adapter.addChildClickViewIds(R.id.iv, R.id.tv_url, R.id.tv_content)
        adapter.setOnItemChildClickListener { adapter, view, position ->
            val article: Article = adapter.getItem(position) as Article
            when (view.id) {
                R.id.iv -> showToast("图片地址：${article.envelopePic}")
                R.id.tv_url -> showToast(article.link)
                R.id.tv_content -> showToast(article.title)
            }
        }

//         val title: TextView = binding.toolbar.getChildAt(0) as TextView
//         title.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
//         title.gravity = Gravity.CENTER

    }

    fun showToast(toast: String) {
        runOnUiThread {
            UniversalToast.makeText(this, toast, UniversalToast.LENGTH_SHORT).showWarning()
        }
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

        articleViewModel.article.observe(this) {
            if (refresh) {
                binding.srl.finishRefresh(true)
                adapter.setList(it.datas)
            } else {
                binding.srl.finishLoadMore()
                adapter.addData(it.datas)
            }
        }

        binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener
        { appBarLayout, verticalOffset ->
            if (verticalOffset > -appBarLayout.totalScrollRange / 2) {
                binding.title.setTextColor(Color.BLACK)
                binding.toolbar.navigationIcon = getDrawable(R.mipmap.action_button_back_pressed)
                binding.title.visibility = View.GONE

                chenJinShi(true)
            } else {
                binding.title.setTextColor(Color.WHITE)
                binding.toolbar.navigationIcon = getDrawable(R.mipmap.action_button_back_normal)
                binding.title.visibility = View.VISIBLE
                chenJinShi(false)
            }
        })

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_share -> {
//                    val shareIntent = ShareCompat.IntentBuilder.from(this)
//                            .setText("shareText")
//                            .setType("text/plain")
//                            .createChooserIntent()
//                            .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
//                    startActivity(shareIntent)
                    ShareModel(this).showBroadView(loadShareParams(), mShareListener)
                    true
                }
                else -> false
            }
        }
    }

    fun initData() {
        articleViewModel.loadArticle(true, 0, true)
    }

    private fun chenJinShi(isLight: Boolean) {
        window.statusBarColor = Color.TRANSPARENT
//        window.navigationBarColor = Color.TRANSPARENT
        if (Build.VERSION.SDK_INT >= 21) {//状态栏导航栏悬浮于activity之上
            var mSystemUI = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE//保持整个View稳定, 常跟bar 悬浮, 隐藏共用, 使View不会因为SystemUI的变化而做layout
//                  or View.SYSTEM_UI_FLAG_LOW_PROFILE//隐藏状态栏图标
//                  or View.SYSTEM_UI_FLAG_FULLSCREEN//隐藏状态栏
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//状态栏悬浮于activity之上
//                  or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR//状态栏字体浅色
//                  or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR//导航栏字体浅色
//                  or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION//隐藏导航栏
//                  or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION//导航栏悬浮于activity之上
//                  or View.SYSTEM_UI_FLAG_IMMERSIVE//呼出隐藏的bar后不会再隐藏掉
//                  or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY//呼出隐藏的bar后会自动再隐藏掉
                    )
            if (isLight) { //白色背景，把状态栏文字图标颜色调成黑色
                mSystemUI = mSystemUI or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR//状态栏字体浅色
//                mSystemUI = mSystemUI or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR//导航栏字体浅色
            }
            window.decorView.systemUiVisibility = mSystemUI
        }
    }


    fun loadShareParams(): ShareParams {
        //这里以分享链接为例
//        val shareParams = ShareParams()
//        shareParams.shareType = Platform.SHARE_WEBPAGE
//        shareParams.title = " 欢迎使用极光社会化组件JShare"
//        shareParams.text = "JShare SDK支持主流社交平台、帮助开发者轻松实现社会化功能！"
//        shareParams.url = "https://www.jiguang.cn"
//        shareParams.imagePath = App.ImagePath

        val params = HashMap<String, Any>()
        params["shareType"] = Platform.SHARE_WEBPAGE
        params["title"] = "欢迎使用极光社会化组件JShare"
        params["text"] = "JShare SDK支持主流社交平台、帮助开发者轻松实现社会化功能！"
        params["url"] = "https://www.jiguang.cn"
        params["imagePath"] = App.ImagePath

        return ShareParams(params)
    }

    private val mShareListener: PlatActionListener = object : PlatActionListener {
        override fun onComplete(platform: Platform, action: Int, data: HashMap<String, Any>) {
            Log.d(TAG, "onComplete-----> p0=${platform.name} action=$action data=$data")
            showToast("分享成功")
        }

        override fun onError(platform: Platform, action: Int, errorCode: Int, error: Throwable) {
            Log.d(TAG, "onError: ----->${Thread.currentThread()}")
            Log.d(TAG, "onError-----> platform=${platform.name} action=$action errorCode=$errorCode error=$error")
            showToast("分享失败")
        }

        override fun onCancel(platform: Platform, action: Int) {
            Log.d(TAG, "onCancel-----> platform=${platform.name} action=$action ")
            showToast("取消分享")
        }
    }


}