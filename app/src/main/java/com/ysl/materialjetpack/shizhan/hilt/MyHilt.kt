package com.ysl.materialjetpack.shizhan.hilt

import android.content.Context
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject
import javax.inject.Qualifier

class MyHilt {
}
class User @Inject constructor(){
    var name: String = ""
    var age: Int = 0
    override fun toString(): String = "name=$name  age=$age"
}
class User1 @Inject constructor(var name : String, var age : Int){
    override fun toString(): String {
        return "name=$name  age=$age"
    }
}
@Module
@InstallIn(ActivityComponent::class)
class UserModule{
    @Provides
    fun provideUser1():User1 = User1("ad", 123)
}
interface Engine{
    fun on()
    fun off()
}
class ChinaEngine @Inject constructor():Engine{
    override fun on() {
        Log.i("zrm", "ChinaEngine on")
    }
    override fun off() {
        Log.i("zrm", "ChinaEngine off")
    }
}
class AmericaEngine @Inject constructor():Engine{
    override fun on() {
        Log.i("zrm", "AmericaEngine on")
    }
    override fun off() {
        Log.i("zrm", "AmericaEngine off")
    }
}
class Car @Inject constructor(val engine:Engine){
    lateinit var name:String
}

/*
@Module
@InstallIn(ActivityComponent::class)
interface HiltActivityModule{
    @Binds
    fun bindEngine(chinaEngine: ChinaEngine):Engine
}*/

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MadeInCN

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MadeInUSA

@Module
@InstallIn(ActivityComponent::class)
class HiltActivityModule{
    @Provides
    @MadeInCN
    fun provideCar(chinaEngine: ChinaEngine):Car{
        return Car(chinaEngine)
    }
    @Provides
    @MadeInUSA
    fun provideEngine1(americaEngine: AmericaEngine):Car{
        return Car(americaEngine)
    }
}



class Work @Inject constructor() {
    lateinit var workName:String
}
data class  Dog @Inject constructor(
//        @ApplicationContext val context: Context,
        @ActivityContext val context: Context,
        val name:String) {
    @Inject lateinit var work: Work

    @EntryPoint
//    @InstallIn(ApplicationComponent::class)
    @InstallIn(ActivityComponent::class)
    interface DogEntries {
        fun provideWork():Work
    }
    init {
        work = EntryPoints.get(context, DogEntries::class.java).provideWork()
    }
}
@Module
@InstallIn(ActivityComponent::class)
class DogModule{
    @Provides
//    fun provideDog(@ApplicationContext context: Context) : Dog = Dog(context,"wang")
    fun provideDog(@ActivityContext context: Context) : Dog = Dog(context,"wang")
}


