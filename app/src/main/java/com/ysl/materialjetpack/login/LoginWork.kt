//package com.ysl.materialjetpack.login
//
//import android.content.Context
//import androidx.hilt.Assisted
//import androidx.work.WorkManager
//import androidx.work.Worker
//import androidx.work.WorkerParameters
//
////在 Worker 对象的构造函数中使用 @WorkerInject 注释来注入一个 Worker。您只能在 Worker 对象中使用 @Singleton 或未限定作用域的绑定。您还必须使用 @Assisted 为 Context 和 WorkerParameters 依赖项添加注释：
//class LoginWork constructor(
//        @Assisted appContext: Context,
//        @Assisted workerParams: WorkerParameters
//) : Worker(appContext, workerParams){
//    override fun doWork(): Result {
//        TODO("Not yet implemented")
//    }
//}