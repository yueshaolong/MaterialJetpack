package com.ysl.materialjetpack.shizhan.model

data class BannerVO (
        var id: Int,
        var title: String,
        var desc: String,
        var type: Int,
        var url: String,
        var imagePath:String
        ){
        override fun toString(): String {
                return "BannerVO(id=$id, title='$title', desc='$desc', type=$type, url='$url', imagePath='$imagePath')"
        }
}