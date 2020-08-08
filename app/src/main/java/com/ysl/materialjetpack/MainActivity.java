package com.ysl.materialjetpack;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory;

public class MainActivity extends AppCompatActivity {

    private NameViewModel model;
    private TextView name;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.tv_name);
        model = new ViewModelProvider(this, new NewInstanceFactory()
                /*new AndroidViewModelFactory(getApplication())*/
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
                name.setText(s);
            }
        };
        model.getCurrentName().observe(this, observer);
        findViewById(R.id.button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                model.getCurrentName().setValue("hahaha"+ (count++));
            }
        });
    }
}
