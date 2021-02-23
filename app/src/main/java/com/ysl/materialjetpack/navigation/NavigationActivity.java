package com.ysl.materialjetpack.navigation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ysl.materialjetpack.R;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_navigation);
//        findViewById(R.id.action_page1);
        //官网代码
//        val finalHost = NavHostFragment.create(R.navigation.nav_graph_main)
//        supportFragmentManager.beginTransaction()
//                .replace(R.id.ll_fragment_navigation, finalHost)
//                .setPrimaryNavigationFragment(finalHost)
//                .commit();
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
//        return NavHostFragment.findNavController(fragment).navigateUp();
//    }
}

