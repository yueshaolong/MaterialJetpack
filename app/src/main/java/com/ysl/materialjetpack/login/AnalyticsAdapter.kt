//package com.ysl.materialjetpack.login
//
//import android.content.Context
//import dagger.hilt.android.qualifiers.ActivityContext
//import javax.inject.Inject
//
////在某个类的构造函数中使用 @Inject 注释，以告知 Hilt 如何提供该类的实例：
//class AnalyticsAdapter constructor(@Inject private val service: AnalyticsService?,
//                                   @ActivityContext private val context: Context//Hilt 提供了一些预定义的限定符。例如，由于您可能需要来自应用或 Activity 的 Context 类，因此 Hilt 提供了 @ApplicationContext 和 @ActivityContext 限定符。
//){
//}