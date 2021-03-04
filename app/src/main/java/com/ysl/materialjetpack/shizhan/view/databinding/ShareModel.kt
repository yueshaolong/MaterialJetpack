package com.ysl.materialjetpack.shizhan.view.databinding

import android.app.Activity
import android.util.Log
import cn.jiguang.share.android.api.JShareInterface
import cn.jiguang.share.android.api.PlatActionListener
import cn.jiguang.share.android.api.ShareParams
import cn.jiguang.share.facebook.Facebook
import cn.jiguang.share.facebook.messenger.FbMessenger
import cn.jiguang.share.jchatpro.JChatPro
import cn.jiguang.share.qqmodel.QQ
import cn.jiguang.share.qqmodel.QZone
import cn.jiguang.share.twitter.Twitter
import cn.jiguang.share.wechat.Wechat
import cn.jiguang.share.wechat.WechatFavorite
import cn.jiguang.share.wechat.WechatMoments
import cn.jiguang.share.weibo.SinaWeibo
import cn.jiguang.share.weibo.SinaWeiboMessage
import com.ysl.sharelibrary.uitool.ShareBoard
import com.ysl.sharelibrary.uitool.ShareBoardlistener
import com.ysl.sharelibrary.uitool.SnsPlatform

class ShareModel(val context: Activity) {

    fun showBroadView(shareParams: ShareParams, mShareListener: PlatActionListener) {
        var mShareBoard: ShareBoard? = null
        if (mShareBoard == null) {
            mShareBoard = ShareBoard(context)
            val platforms = JShareInterface.getPlatformList()
            for (platform in platforms) {
                println("------->$platform")
            }
            if (platforms != null) {
                val var2: Iterator<*> = platforms.iterator()
                while (var2.hasNext()) {
                    val temp = var2.next() as String
//                    if (temp == WechatFavorite.Name) continue //不展示微信收藏
                    val snsPlatform: SnsPlatform = createSnsPlatform(temp)
                    mShareBoard.addPlatform(snsPlatform)
                }
            }
            mShareBoard.setShareboardclickCallback(ShareBoardlistener { snsPlatform, platform ->
                JShareInterface.share(platform, shareParams, mShareListener)
            })
        }
        mShareBoard.show()
    }

    private fun createSnsPlatform(platformName: String): SnsPlatform {
        Log.d(MListActivity.TAG, "createSnsPlatform: platformName = $platformName")
        var mShowWord = platformName
        var mIcon = ""
        var mGrayIcon = ""
        if (platformName == Wechat.Name) {
            mIcon = "jiguang_socialize_wechat"
            mGrayIcon = "jiguang_socialize_wechat"
            mShowWord = "jiguang_socialize_text_weixin_key"
        } else if (platformName == WechatMoments.Name) {
            mIcon = "jiguang_socialize_wxcircle"
            mGrayIcon = "jiguang_socialize_wxcircle"
            mShowWord = "jiguang_socialize_text_weixin_circle_key"
        } else if (platformName == WechatFavorite.Name) {
            mIcon = "jiguang_socialize_wxfavorite"
            mGrayIcon = "jiguang_socialize_wxfavorite"
            mShowWord = "jiguang_socialize_text_weixin_favorite_key"
        } else if (platformName == SinaWeibo.Name) {
            mIcon = "jiguang_socialize_sina"
            mGrayIcon = "jiguang_socialize_sina"
            mShowWord = "jiguang_socialize_text_sina_key"
        } else if (platformName == SinaWeiboMessage.Name) {
            mIcon = "jiguang_socialize_sina"
            mGrayIcon = "jiguang_socialize_sina"
            mShowWord = "jiguang_socialize_text_sina_msg_key"
        } else if (platformName == QQ.Name) {
            mIcon = "jiguang_socialize_qq"
            mGrayIcon = "jiguang_socialize_qq"
            mShowWord = "jiguang_socialize_text_qq_key"
        } else if (platformName == QZone.Name) {
            mIcon = "jiguang_socialize_qzone"
            mGrayIcon = "jiguang_socialize_qzone"
            mShowWord = "jiguang_socialize_text_qq_zone_key"
        } else if (platformName == Facebook.Name) {
            mIcon = "jiguang_socialize_facebook"
            mGrayIcon = "jiguang_socialize_facebook"
            mShowWord = "jiguang_socialize_text_facebook_key"
        } else if (platformName == FbMessenger.Name) {
            mIcon = "jiguang_socialize_messenger"
            mGrayIcon = "jiguang_socialize_messenger"
            mShowWord = "jiguang_socialize_text_messenger_key"
        } else if (platformName == Twitter.Name) {
            mIcon = "jiguang_socialize_twitter"
            mGrayIcon = "jiguang_socialize_twitter"
            mShowWord = "jiguang_socialize_text_twitter_key"
        } else if (platformName == JChatPro.Name) {
            mShowWord = "jiguang_socialize_text_jchatpro_key"
        }
        return ShareBoard.createSnsPlatform(mShowWord, platformName, mIcon, mGrayIcon, 0)
    }
}