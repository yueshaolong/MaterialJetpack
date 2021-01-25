//package com.ysl.materialjetpack.login
//
//import androidx.lifecycle.LiveData
//import com.ysl.materialjetpack.login.User
//import retrofit2.Response
//import retrofit2.http.GET
//import retrofit2.http.Path
//
//interface UserService {
//    @GET("/users/{user}")
//    fun getUser(@Path("user") userId: String): LiveData<Response<User>>
//}