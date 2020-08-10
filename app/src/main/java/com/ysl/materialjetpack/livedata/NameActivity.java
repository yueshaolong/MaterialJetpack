package com.ysl.materialjetpack.livedata;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ysl.materialjetpack.R;


public class NameActivity extends AppCompatActivity {

    private NameViewModel model;

    private TextView nameTextView;
    private Button btn;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_name);
        nameTextView=findViewById(R.id.tvText);
        btn=findViewById(R.id.btn);

        model= new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(NameViewModel.class);

        //需要一个观察者来观察数据
        Observer observer=new Observer<String>(){
            @Override
            public void onChanged(String s) {
                nameTextView.setText(s);
            }
        };

        //订阅
        model.getMutableLiveData().observe(this,observer);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                model.i++;
                model.getMutableLiveData().setValue(model.i+"");
            }
        });

    }
}
