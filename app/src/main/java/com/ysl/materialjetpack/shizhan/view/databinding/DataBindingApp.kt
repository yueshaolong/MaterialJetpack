package com.ysl.materialjetpack.shizhan.view.databinding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object DataBindingApp {
    @BindingAdapter("app:imgUrl")
    @JvmStatic
    fun setImageView(view: ImageView, url: String){
        Glide.with(view.context).load(url).centerCrop().into(view)
    }

    @BindingAdapter("app:goneUnless")
    @JvmStatic
    fun goneUnless(view: View, visible: Boolean){
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }
}