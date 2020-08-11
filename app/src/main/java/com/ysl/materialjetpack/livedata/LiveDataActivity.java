package com.ysl.materialjetpack.livedata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ysl.materialjetpack.R;
import com.ysl.materialjetpack.lifecycle.databus.LiveDataBus;

public class LiveDataActivity extends AppCompatActivity {

    private NameViewModel model;
    private TextView tv_name;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_livedata);
        tv_name = findViewById(R.id.tv_name);
        model = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
                /*new NewInstanceFactory()*/
                /*new Factory() {
                    @NonNull
                    @Override
                    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                        return (T) new NameViewModel();
                    }
                }*/
                ).get(NameViewModel.class);
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tv_name.setText(s);
            }
        };
        model.getMutableLiveData().observe(this, observer);
        findViewById(R.id.button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                model.getMutableLiveData().setValue("实时更新："+ (count++));
            }
        });
    }

    public void click(View view) {
        if (view.getId() == R.id.btn) {
            Intent intent = new Intent(this, NameActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.btn2) {
            Intent intent = new Intent(this, TestLiveDataBusActivity.class);
            startActivity(intent);
            new Thread(){
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        //发送消息
                        LiveDataBus.getInstance().with("data",String.class)
                                .postValue("jett"+i);
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }
}
