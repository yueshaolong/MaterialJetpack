package com.ysl.materialjetpack.shizhan

import android.util.Log

const val TAG = "abc"
class OutIn {
    open class Animal
    class Dog : Animal()

    interface Box<T> {
        fun getAnimal(): T
        fun putAnimal(a: T)
    }

    /** 协变
     * b 属于Animal的子类，此方法传入Animal的子类
     * out 代替了? extends，传入子类，只能获取属性
     */
    fun getAnimalFromBox(b: Box<out Animal>) : Animal {
        val animal: Animal = b.getAnimal()
//    b.putAnimal(Nothing) 无法调用，因为方法需要一个Nothing类型的对象，但是在kotlin中无法获取
        return animal
    }

    /** 逆变
     * b 属于Dog的父类，此方法传入Dog的父类
     * in 代替了? super，传入父类，可以修改属性
     */
    fun putAnimalInBox(b: Box<in Dog>){
        b.putAnimal(Dog())
//        val animal:Any? = b.getAnimal()// 可以调用读取方法，但是返回的类型确实Any?，因为我们只能确定？的大基类是Any?
    }


    inline fun print(message: Int) {
        System.out.print(message)
    }
    fun main(args: Array<String>) {
        print(2)
        print(2)

        repeat(100) {
            println("A")
        }
    }

//    public inline fun <T, R> T.let(block: (T) -> R): R = block(this)
//    public inline fun <T> T.also(block: (T) -> Unit): T { block(this); return this }
//
//    public inline fun <T, R> with(receiver: T, block: T.() -> R): R = receiver.block()
//
//    public inline fun <T, R> T.run(block: T.() -> R): R = block()
//    public inline fun <T> T.apply(block: T.() -> Unit): T { block(); return this }

    public inline fun <T> T.doWithTry(block: () -> Unit) {
        try {
            block()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    //定义了一个Person类
    class Person(val name:String){
        var age:Int = 0
        var sex:Int = 0
    }

    fun test() {
        val person= Person("张三")
//        2、(T) -> Unit 将T表示的对象作为实参通过函数参数传递进来，供函数体使用
        person.also {
            //没有指定参数名字,必须用it代指参数
            it.age = 20   //it不能省略
            it.sex = 0    //it不能省略
        }
//        2、(T) -> Unit 将T表示的对象作为实参通过函数参数传递进来，供函数体使用
        //或者
        person.also {personValue->
            //使用指定的参数名，同样personValue不能省略
            personValue.age = 20
            personValue.sex = 0
        }

//        1、T.()->Unit 的函数体中可以直接使用T代表的对象，即用this代表对象
        person.apply {
            //直接访问Person的属性
            this.age = 20  //this可以省略
            this.sex = 1   //this可以省略
        }
//        1、T.()->Unit 的函数体中可以直接使用T代表的对象，即用this代表对象
        person.apply {
            //直接访问Person的属性
            age = 20  //this可以省略
            sex = 1   //this可以省略
        }

//        3、 ()->Unit与T表示的对象没有直接联系，只能通过外部T实例的变量来访问对象
        person.doWithTry{
            //只能通过外部变量来访问Person
            person.age = 20
            person.sex = 1
        }

        val status = true
        val someObject = null
        someObject?.takeIf {
            status
        }?.apply {
            test()
        }
    }

    fun testTakeIf() {
        val name = "yanzhikai"
        val index = name.indexOf("yan")

        index.takeIf {
            Log.i(TAG, "testTakeIf: it = $it")
            it >= 0
        }?.let {
            Log.i(TAG, "testTakeIf: has yan")
        }

        name.indexOf("yan").takeUnless {
            it >= 0
        }?.also {

        }
        name.also {

        }
    }

}