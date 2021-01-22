package com.ysl.myapplication.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class UserViewModel(savedStateHandle: SavedStateHandle,
                    userRepository: UserRepository
) :ViewModel(){
//    val userId : String = TODO()
//    val user : User = TODO()
    val userId : String = savedStateHandle["userId"] ?: throw IllegalArgumentException("missing user userId")
    val user : LiveData<User> = userRepository.getUser("123")
}
