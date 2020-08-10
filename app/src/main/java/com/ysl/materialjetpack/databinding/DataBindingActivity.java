package com.ysl.materialjetpack.databinding;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.ysl.materialjetpack.BR;
import com.ysl.materialjetpack.R;

public class DataBindingActivity extends AppCompatActivity {

    User user;
    private ActDatabindingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.act_databinding);
        user = new User("jett","123");
        binding.setUser(user);

        new Thread(){
            @Override
            public void run() {
                super.run();

                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    user.setName(user.getName()+i);
                    binding.setUser(user);
                    binding.setVariable(BR.user,user);
                }
            }
        }.start();
    }
}
