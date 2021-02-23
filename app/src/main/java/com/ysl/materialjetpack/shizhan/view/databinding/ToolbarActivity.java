package com.ysl.materialjetpack.shizhan.view.databinding;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ysl.materialjetpack.R;


public class ToolbarActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);


        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        toolbar.setNavigationOnClickListener(view -> {
            Toast.makeText(this, "返回", Toast.LENGTH_SHORT).show();
        });

        //透明状态栏效果
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

        }
    }
}
