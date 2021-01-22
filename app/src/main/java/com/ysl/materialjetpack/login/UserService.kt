package com.ysl.myapplication.login

import androidx.lifecycle.LiveData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("/users/{user}")
    fun getUser(@Path("user") userId: String): LiveData<Response<User>>
}