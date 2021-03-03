package com.ysl.materialjetpack.shizhan.view.databinding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BaseActivity2.requestPermission(this){
            granted ->
                if (granted) {
                    startActivity(Intent(this, WanActivity2::class.java))
                    finish()
                } else {
                    BaseActivity2.showDialog()
                }
        }
    }
}