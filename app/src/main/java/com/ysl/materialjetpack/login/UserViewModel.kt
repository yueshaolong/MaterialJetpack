//package com.ysl.materialjetpack.login
//
//import androidx.hilt.Assisted
//import androidx.hilt.lifecycle.ViewModelInject
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.SavedStateHandle
//import androidx.lifecycle.ViewModel
//import com.ysl.materialjetpack.login.User
//import com.ysl.materialjetpack.login.UserRepository
//
//class UserViewModel(savedStateHandle: SavedStateHandle,
//                    userRepository: UserRepository
//) :ViewModel(){
////    val userId : String = TODO()
////    val user : User = TODO()
//    val userId : String = savedStateHandle["userId"] ?: throw IllegalArgumentException("missing user userId")
//    val user : LiveData<User> = userRepository.getUser("123")
//}
//
////在 ViewModel 对象的构造函数中使用 @ViewModelInject 注释来提供一个 ViewModel。
//// 您还必须使用 @Assisted 为 SavedStateHandle 依赖项添加注释：
////class ExampleViewModel @ViewModelInject constructor(
////        @Assisted private val savedStateHandle: SavedStateHandle,
////        private val userRepository: UserRepository
////) : ViewModel() {
////    val userId : String = savedStateHandle["userId"] ?: throw IllegalArgumentException("missing user userId")
////    val user : LiveData<User> = userRepository.getUser("123")
////}
