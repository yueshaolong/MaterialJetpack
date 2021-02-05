package com.ysl.materialjetpack.shizhan

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
}