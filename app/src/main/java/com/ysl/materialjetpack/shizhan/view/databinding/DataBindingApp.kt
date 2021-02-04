package com.ysl.materialjetpack.shizhan.view.databinding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ysl.materialjetpack.R
import com.ysl.materialjetpack.shizhan.util.GlideApp


object DataBindingApp {
    @BindingAdapter("app:imgUrl")
    @JvmStatic
    fun setImageView(view: ImageView, url: String){

//        val options: RequestOptions = RequestOptions()
//                .placeholder(R.mipmap.ic_launcher)

        GlideApp.with(view).load(url).placeholder(R.mipmap.ic_launcher).error(R.mipmap.s5).centerCrop().into(view)
    }

    @BindingAdapter("app:goneUnless")
    @JvmStatic
    fun goneUnless(view: View, visible: Boolean){
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }
}