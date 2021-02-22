package com.ysl.materialjetpack.shizhan.hilt

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ysl.materialjetpack.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HiltActivity : AppCompatActivity(){
    @Inject lateinit var user: User
    @Inject lateinit var user1: User1
    @Inject @MadeInCN lateinit var car: Car
    @Inject @MadeInUSA lateinit var car1: Car

    @Inject lateinit var dog: Dog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_hilt)

        findViewById<Button>(R.id.btn).setOnClickListener {
            user.name = "ss"
            user.age = 10
            car.name = "byd"

            findViewById<TextView>(R.id.tv).text = "${user}---${car.name}---${user1}"
            car.engine.on()
            car.engine.off()
            car1.engine.on()
            car1.engine.off()

            dog.work.workName = "导盲"
            Log.i("TAG", "onCreate: ${dog.work.workName}")
        }
    }
}