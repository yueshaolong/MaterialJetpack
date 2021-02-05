package com.ysl.materialjetpack.shizhan.view.databinding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ysl.materialjetpack.R
import com.ysl.materialjetpack.shizhan.util.GlideApp


object DataBindingApp {
    @BindingAdapter("app:imgUrl")
    @JvmStatic
    fun setImageView(view: ImageView, url: String){
        GlideApp.with(view).load(url).placeholder(R.mipmap.ic_launcher).error(R.mipmap.error).centerCrop().into(view)
    }

    @BindingAdapter("app:goneUnless")
    @JvmStatic
    fun goneUnless(view: View, visible: Boolean){
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @BindingAdapter("app:layoutManager", "app:spanCount")
    @JvmStatic
    fun sLayoutManager(view: RecyclerView, layoutManager: RecyclerView.LayoutManager, spanCount: Int){
        (layoutManager as GridLayoutManager).spanCount = spanCount
        view.layoutManager = layoutManager
    }
}