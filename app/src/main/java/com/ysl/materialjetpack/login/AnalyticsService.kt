//package com.ysl.materialjetpack.login
//
//import android.content.Context
//import androidx.appcompat.app.AppCompatActivity
//import dagger.Binds
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.AndroidEntryPoint
//import dagger.hilt.android.components.ActivityComponent
//import dagger.hilt.android.qualifiers.ApplicationContext
//import okhttp3.Interceptor
//import okhttp3.OkHttpClient
//import okhttp3.Response
//import retrofit2.Retrofit
//import javax.inject.Inject
//import javax.inject.Qualifier
//
//interface AnalyticsService {
//    fun analyticsMethods()
//}
//
//// Constructor-injected, because Hilt needs to know how to
//// provide instances of AnalyticsServiceImpl, too.
//class AnalyticsServiceImpl constructor
//(@ApplicationContext context: Context) : AnalyticsService {
//    override fun analyticsMethods() {
//        TODO("Not yet implemented")
//    }
//}
//
////如果您需要让 Hilt 以依赖项的形式提供同一类型的不同实现，必须向 Hilt 提供多个绑定。您可以使用限定符为同一类型定义多个绑定。
//@Qualifier//定义要用于为 @Binds 或 @Provides 方法添加注释的限定符：
//@Retention(AnnotationRetention.BINARY)
//annotation class AuthInterceptorOkHttpClient
//@Qualifier//定义要用于为 @Binds 或 @Provides 方法添加注释的限定符：
//@Retention(AnnotationRetention.BINARY)
//annotation class OtherInterceptorOkHttpClient
//
//@Module//标记 hilt 模块
//@InstallIn(ActivityComponent::class)//AnalyticsModule 中的所有依赖项都可以在应用的所有 Activity 中使用。
//abstract class AnalyticsModule {
//    @Binds//AnalyticsService 是一个接口，则您无法通过构造函数注入它，而应向 Hilt 提供绑定信息，方法是在 Hilt 模块内创建一个带有 @Binds 注释的抽象函数。
//    abstract fun bindAnalyticsService(
//            analyticsServiceImpl: AnalyticsServiceImpl
//    ): AnalyticsService
//
//    @Provides//如果 AnalyticsService 类不直接归您所有，您可以告知 Hilt 如何提供此类型的实例，方法是在 Hilt 模块内创建一个函数，并使用 @Provides 为该函数添加注释。
//    fun provideAnalyticsService(
//            // 这种类型的潜在依赖性
//    ): AnalyticsService {
//        return Retrofit.Builder()
//                .baseUrl("https://example.com")
//                .build()
//                .create(AnalyticsService::class.java)
//    }
//
//    //下面两种方法具有相同的返回类型，但限定符将它们标记为两个不同的绑定：
//    @AuthInterceptorOkHttpClient
//    @Provides
//    fun provideAuthInterceptorOkHttpClient(
//            authInterceptor: AuthInterceptor
//    ): OkHttpClient {
//        return OkHttpClient.Builder()
//                .addInterceptor(authInterceptor)
//                .build()
//    }
//    @OtherInterceptorOkHttpClient
//    @Provides
//    fun provideOtherInterceptorOkHttpClient(
//            otherInterceptor: OtherInterceptor
//    ): OkHttpClient {
//        return OkHttpClient.Builder()
//                .addInterceptor(otherInterceptor)
//                .build()
//    }
//    class AuthInterceptor:Interceptor{
//        override fun intercept(chain: Interceptor.Chain): Response {
//            TODO("Not yet implemented")
//        }
//    }
//    class OtherInterceptor:Interceptor{
//        override fun intercept(chain: Interceptor.Chain): Response {
//            TODO("Not yet implemented")
//        }
//    }
//    //您可以通过使用相应的限定符为字段或参数添加注释来注入所需的特定类型：
//    @Provides
//    fun provideAnalyticsService(//通过构造限定类型
//            @AuthInterceptorOkHttpClient okHttpClient: OkHttpClient
//    ): AnalyticsService {
//        return Retrofit.Builder()
//                .baseUrl("https://example.com")
//                .client(okHttpClient)
//                .build()
//                .create(AnalyticsService::class.java)
//    }
//}
//
//// 通过构造依赖
//class ExampleServiceImpl @Inject constructor(
//        @AuthInterceptorOkHttpClient private val okHttpClient: OkHttpClient
//) : AnalyticsService{
//    override fun analyticsMethods() {
//        TODO("Not yet implemented")
//    }
//
//}
//
//// 直接属性注入
//@AndroidEntryPoint
//class ExampleActivity: AppCompatActivity() {
//    @AuthInterceptorOkHttpClient
//    @Inject lateinit var okHttpClient: OkHttpClient
//}