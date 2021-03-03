package com.ysl.materialjetpack.shizhan.view.databinding

import android.app.ProgressDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import cn.jiguang.share.android.api.JShareInterface
import cn.jiguang.share.android.api.PlatActionListener
import cn.jiguang.share.android.api.Platform
import cn.jiguang.share.android.api.ShareParams
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.google.android.material.appbar.AppBarLayout
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
    companion object{
        const val TAG = "MListActivity"
    }

    private val articleViewModel : ArticleViewModel by viewModels()
    private val toolBarViewModel : ToolBarViewModel by viewModels()
    private var refresh: Boolean = true
    private lateinit var progressDialog: ProgressDialog
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
         progressDialog = ProgressDialog(this)
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

//         val title: TextView = binding.toolbar.getChildAt(0) as TextView
//         title.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
//         title.gravity = Gravity.CENTER

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
             when(item.itemId){
                 R.id.action_share -> {
                     Log.i(TAG, "分享------>")
                     val platformList = JShareInterface.getPlatformList()
                     for (s in platformList) {
                         Log.i(TAG, "initEvent: s=$s")
                         //微信：Wechat，WechatMoments，WechatFavorite
                         //QQ：QQ，QZone
                     }
                     val shareParams = ShareParams()
                     shareParams.shareType = Platform.SHARE_WEBPAGE
                     shareParams.title = " 欢迎使用极光社会化组件JShare"
                     shareParams.text = "JShare SDK支持主流社交平台、帮助开发者轻松实现社会化功能！"
                     shareParams.url = "https://www.jiguang.cn"
//                     shareParams.imagePath = "./MaterialJetpack\\app\\src\\main\\res\\mipmap-hdpi\\error.jpg"
                     JShareInterface.share(platformList[0], shareParams, object : PlatActionListener{
                         override fun onComplete(p0: Platform?, p1: Int, p2: HashMap<String, Any>?) {
                            showToast("分享成功")
                         }

                         override fun onError(p0: Platform?, p1: Int, p2: Int, p3: Throwable?) {
                            showToast("分享失败")
                         }

                         override fun onCancel(p0: Platform?, p1: Int) {
                             showToast("取消分享")
                         }
                     })
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
            if (isLight){ //白色背景，把状态栏文字图标颜色调成黑色
                mSystemUI = mSystemUI or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR//状态栏字体浅色
//                mSystemUI = mSystemUI or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR//导航栏字体浅色
            }
            window.decorView.systemUiVisibility = mSystemUI
        }
    }


//    private fun showBroadView() {
//        var mShareBoard: ShareBoard? = null
//        if (mShareBoard == null) {
//            mShareBoard = ShareBoard(this)
//            val platforms = JShareInterface.getPlatformList()
//            if (platforms != null) {
//                val var2: Iterator<*> = platforms.iterator()
//                while (var2.hasNext()) {
//                    val temp = var2.next() as String
//                    val snsPlatform: SnsPlatform = createSnsPlatform(temp)
//                    mShareBoard.addPlatform(snsPlatform)
//                }
//            }
//            mShareBoard.setShareboardclickCallback(mShareBoardlistener)
//        }
//        progressDialog.setTitle("请稍候")
//        mShareBoard.show()
//    }
//
//    private val mShareBoardlistener: ShareBoardlistener = ShareBoardlistener { snsPlatform, platform ->
//        progressDialog.show()
//        //这里以分享链接为例
//        val shareParams = ShareParams()
//        shareParams.shareType = Platform.SHARE_WEBPAGE
//        shareParams.title = " 欢迎使用极光社会化组件JShare"
//        shareParams.text = "JShare SDK支持主流社交平台、帮助开发者轻松实现社会化功能！"
//        shareParams.url = "https://www.jiguang.cn"
////        shareParams.imagePath = "F:\\ThirdPartyProject\\MaterialJetpack\\app\\src\\main\\res\\mipmap-hdpi\\error.jpg"
//        JShareInterface.share(platform, shareParams, mShareListener)
//    }
//
//    private val mShareListener: PlatActionListener = object : PlatActionListener {
//        override fun onComplete(platform: Platform, action: Int, data: HashMap<String, Any>) {
//            if (handler != null) {
//                val message: Message = handler.obtainMessage()
//                message.obj = "分享成功"
//                handler.sendMessage(message)
//            }
//        }
//        private val handler: Handler = object : Handler() {
//            @SuppressLint("HandlerLeak")
//            override fun handleMessage(msg: Message) {
//                val toastMsg = msg.obj as String
//                Toast.makeText(this@MListActivity, toastMsg, Toast.LENGTH_SHORT).show()
//                if (progressDialog != null && progressDialog.isShowing()) {
//                    progressDialog.dismiss()
//                }
//            }
//        }
//        override fun onError(platform: Platform, action: Int, errorCode: Int, error: Throwable) {
//            Logger.e(TAG, "error:$errorCode,msg:$error")
//            if (handler != null) {
//                val message: Message = handler.obtainMessage()
//                message.obj = "分享失败:" + error.message + "---" + errorCode
//                handler.sendMessage(message)
//            }
//        }
//
//        override fun onCancel(platform: Platform, action: Int) {
//            if (handler != null) {
//                val message: Message = handler.obtainMessage()
//                message.obj = "分享取消"
//                handler.sendMessage(message)
//            }
//        }
//    }
//
//
//    fun createSnsPlatform(platformName: String): SnsPlatform {
//        var mShowWord = platformName
//        var mIcon = ""
//        var mGrayIcon = ""
//        if (platformName == Wechat.Name) {
//            mIcon = "jiguang_socialize_wechat"
//            mGrayIcon = "jiguang_socialize_wechat"
//            mShowWord = "jiguang_socialize_text_weixin_key"
//        } else if (platformName == WechatMoments.Name) {
//            mIcon = "jiguang_socialize_wxcircle"
//            mGrayIcon = "jiguang_socialize_wxcircle"
//            mShowWord = "jiguang_socialize_text_weixin_circle_key"
//        } else if (platformName == WechatFavorite.Name) {
//            mIcon = "jiguang_socialize_wxfavorite"
//            mGrayIcon = "jiguang_socialize_wxfavorite"
//            mShowWord = "jiguang_socialize_text_weixin_favorite_key"
//        } /*else if (platformName == SinaWeibo.Name) {
//            mIcon = "jiguang_socialize_sina"
//            mGrayIcon = "jiguang_socialize_sina"
//            mShowWord = "jiguang_socialize_text_sina_key"
//        } else if (platformName == SinaWeiboMessage.Name) {
//            mIcon = "jiguang_socialize_sina"
//            mGrayIcon = "jiguang_socialize_sina"
//            mShowWord = "jiguang_socialize_text_sina_msg_key"
//        } else if (platformName == QQ.Name) {
//            mIcon = "jiguang_socialize_qq"
//            mGrayIcon = "jiguang_socialize_qq"
//            mShowWord = "jiguang_socialize_text_qq_key"
//        } else if (platformName == QZone.Name) {
//            mIcon = "jiguang_socialize_qzone"
//            mGrayIcon = "jiguang_socialize_qzone"
//            mShowWord = "jiguang_socialize_text_qq_zone_key"
//        } else if (platformName == Facebook.Name) {
//            mIcon = "jiguang_socialize_facebook"
//            mGrayIcon = "jiguang_socialize_facebook"
//            mShowWord = "jiguang_socialize_text_facebook_key"
//        } else if (platformName == FbMessenger.Name) {
//            mIcon = "jiguang_socialize_messenger"
//            mGrayIcon = "jiguang_socialize_messenger"
//            mShowWord = "jiguang_socialize_text_messenger_key"
//        } else if (Twitter.Name.equals(platformName)) {
//            mIcon = "jiguang_socialize_twitter"
//            mGrayIcon = "jiguang_socialize_twitter"
//            mShowWord = "jiguang_socialize_text_twitter_key"
//        } else if (platformName == JChatPro.Name) {
//            mShowWord = "jiguang_socialize_text_jchatpro_key"
//        }*/
//        return ShareBoard.createSnsPlatform(mShowWord, platformName, mIcon, mGrayIcon, 0)
//    }
}