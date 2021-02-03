package com.ysl.materialjetpack.shizhan.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Headers

interface FileApi {

    //http://39.104.137.131:9301/dynafile/download/20d12664ed4e4ab1ab0f1933cd8d6e38nstest
    //http://39.104.137.131:9995/versions/files/NewBlueSky_xsbn/image-d
    @GET("versions/files/NewBlueSky_xsbn/image-d")
    @Headers("Accept-Encoding: application/octet-stream",
            "Content-Type: application/octet-stream")
    fun download() : Observable<ResponseBody>

    //http://39.104.137.131:9301/dynafile/download/20d12664ed4e4ab1ab0f1933cd8d6e38nstest
    @GET("versions/files/NewBlueSky_xsbn/image-d")
    @Headers("Accept-Encoding: application/octet-stream",
            "Content-Type: application/octet-stream")
    fun upload() : Observable<ResponseBody>

}